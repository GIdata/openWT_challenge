import {defineStore} from "pinia";
import Boat from "../domain/Boat";
import {reactive} from "vue";
import NetworkService from "./NetworkService";

export const useStore = defineStore('store', {
    state: () => {
        return {
            networkService: new NetworkService(new URL(import.meta.env.VITE_SERVER_URL)),
            boats: reactive<Boat[]>([]),
            selectedBoat: reactive<{ boat: null | Boat, editable: boolean }>({boat: null, editable: false})
        }
    },
    actions: {
        removeBoat(boat: Boat) {
            this.boats.splice(this.boats.indexOf(boat), 1);
        },
        clearSelectedBoat() {
            this.selectedBoat.boat = null;
            this.selectedBoat.editable = false;
        },

        selectBoat(boat: Boat) {
            this.selectedBoat.boat = boat;
            this.selectedBoat.editable = false;
        },

        editBoat(boat: Boat) {
            this.selectedBoat.boat = boat;
            this.selectedBoat.editable = true;
        },

        fetchBoats() {
            console.log("fetchBoats")
            this.networkService.sendRequest("GET", undefined, (response) => {
                this.boats.length = 0;
                let boatList = JSON.parse(response) as Array<Boat>
                boatList.forEach(b => {
                    this.boats.push(new Boat(b.name, b.description, b.id))
                })
            });
        },

        createBoat(name: string, description: string) {
            console.log("createBoat", name, description)
            const requestBody = JSON.stringify({name: name, description: description});
            this.networkService.sendRequest("POST", requestBody, (response) => {
                let boat = new Boat(name, description, parseInt(response))
                this.boats.push(boat)
            })
        },

        deleteBoat(boat: Boat) {
            console.log("deleteBoat", boat.id)
            this.networkService.sendRequest("DELETE", undefined,
                (_) => this.removeBoat(boat),
                (error, errorMessage) => {
                    alert(`Could not delete boat with id ${boat.id}\nServer Error(${error}): ${errorMessage}`)
                },
                boat.id.toString());
        },

        updateBoat(boat: Boat, name: string, description: string) {
            console.log("updateBoat", boat.id, name, description)
            const requestBody = JSON.stringify({name: name, description: description});
            this.networkService.sendRequest("PUT", requestBody, (_) => {
                boat.name = name;
                boat.description = description;
            }, undefined, boat.id.toString());
        }


    }
})