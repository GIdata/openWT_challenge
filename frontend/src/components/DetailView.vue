<script lang="ts" setup xmlns="http://www.w3.org/1999/html">
import {onMounted} from "vue";
import {useStore} from "../services/Store";

onMounted(() => {
  document.getElementById("detail-view")?.focus()
})

const store = useStore()

const saveAndExit = () => {
  const name = document.getElementById("boat-name")?.innerText ?? ""
  const description = document.getElementById("boat-description")?.innerText ?? ""
  if (store.selectedBoat.boat !== null) {
    store.updateBoat(store.selectedBoat.boat, name, description)
  }
  store.clearSelectedBoat()
}

</script>

<template>
  <div id="detail-view"
       :contenteditable="store.selectedBoat.editable">
    <h1 id="boat-name">{{ store.selectedBoat.boat?.name }}</h1>
    <p id="boat-description">{{ store.selectedBoat.boat?.description }}</p>
    <div id="action-buttons">
      <div v-if="store.selectedBoat.editable">
        <button v-on:click="saveAndExit()">Save and Close</button>
        <button v-on:click="store.clearSelectedBoat()">Close</button>
      </div>
      <div v-else>
        <button v-on:click="store.editBoat(store.selectedBoat.boat)">Edit</button>
        <button v-on:click="store.clearSelectedBoat()">Close</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
#detail-view {
  position: relative;
  margin: auto;
  width: 60vw;
  height: 60vh;
  background-color: var(--background-color);
  color: var(--on-background-color);
  padding: 1em 2em;
}

#action-buttons {
  position: absolute;
  bottom: 30px;
  right: 30px;
}

#action-buttons button {
  padding: 0.2em 1em;
  margin-left: 5px;
}
</style>