export default class NetworkService {
    readonly serverURL: URL;
    lastCSRFToken: string | null = null;

    constructor(serverUrl: URL) {
        this.serverURL = serverUrl;
        this.extractCSRFTokenFromString(document.cookie)
    }

    sendRequest(method: ("GET" | "POST" | "PUT" | "DELETE"),
                body: any,
                callback: (response: any) => void,
                errorCallback?: (error: number, errorMessage: any) => void,
                slug = "") {
        let request = new XMLHttpRequest()
        request.open(method, this.serverURL + slug, true)
        request.setRequestHeader("Content-Type", "application/json")
        if (this.lastCSRFToken !== null) {
            request.setRequestHeader("X-XSRF-TOKEN", this.lastCSRFToken)
        }
        request.onreadystatechange = () => {
            if (request.readyState == XMLHttpRequest.DONE) {
                this.extractCSRFToken(request)
                if (request.status == 200) {
                    callback(request.response)
                } else if (errorCallback) {
                    let json = JSON.parse(request.response) as object;
                    if (json?.hasOwnProperty("message")) {
                        // @ts-ignore
                        errorCallback(request.status, json.message)
                    } else {
                        errorCallback(request.status, request.response)
                    }
                }
            }
        }
        request.send(body)
    }

    extractCSRFToken(request: XMLHttpRequest) {
        const cookies = request.getResponseHeader("Cookie")
        if (cookies !== null) this.extractCSRFTokenFromString(cookies)
    }

    extractCSRFTokenFromString(cookies: string) {
        const token = cookies.split(";")?.filter(c => c.trim().startsWith("XSRF-TOKEN"))[0]?.trim()
        if (token === undefined) return;
        this.lastCSRFToken = token.substring(token.indexOf("=") + 1)
    }
}