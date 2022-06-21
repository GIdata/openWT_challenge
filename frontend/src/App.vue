<script lang="ts" setup>

import BoatTable from "./components/BoatTable.vue";
import AddBoatElement from "./components/AddBoatElement.vue";
import DetailView from "./components/DetailView.vue";
import {useStore} from "./services/Store";

const store = useStore()
store.fetchBoats()
</script>

<template>
  <h1>OpenWT Boats</h1>
  <DetailView v-if="store.selectedBoat.boat !== null"/>
  <div v-else>
    <AddBoatElement/>
    <BoatTable/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
  padding: 1em 3em;

  --background-color: #e5f9ff;
  --on-background-color: #333333;
  --background-color-darker: #c4f0ff;
  --warning-color: lightcoral;
}
</style>
