<script lang="ts" setup>
import BoatTableEntry from "./BoatTableEntry.vue";
import {useStore} from "../services/Store";

const store = useStore()

const reloadBoats = () => {
  store.fetchBoats()
  const image = document.getElementById("reload-button")
  if (image !== null) {
    // restart the turn animation
    image.style.animation = "none";
    setTimeout(() => image.style.animation = "", 10);
  }
}

</script>
<template>
  <div id="boat-table-header" class="boat-container">
    <h2 class="boat-table-col0">ID</h2>
    <h2 class="boat-table-col1">Boat Name</h2>
    <h2 class="boat-table-col2">Description</h2>
    <span class="boat-table-col3">
      <img id="reload-button" alt="reload boats" src="../assets/reload.svg" v-on:click="reloadBoats()"/>
    </span>
  </div>
  <BoatTableEntry v-for="boat in store.boats" :boat="boat" class="boat-container"></BoatTableEntry>
</template>


<style>
#boat-table-header {
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: var(--background-color-darker);
}

.boat-table-col0 {
  width: 2%;
  min-width: 25px;
  font-weight: bold;
  opacity: 0.6;
}

.boat-table-col1 {
  width: 25%;
}

.boat-table-col2 {
  width: 60%;
}

.boat-table-col3 {
  width: 5%;
}

#reload-button {
  float: right;
  height: 25px;
  animation-name: turn-reload-button;
  animation-duration: 1s;
  animation-play-state: initial;
}

@keyframes turn-reload-button {
  from {
    rotate: 0deg;
  }
  to {
    rotate: 360deg;
  }
}

.boat-container {
  background-color: var(--background-color);
  color: var(--on-background-color);
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: baseline;
  flex-wrap: wrap;
  padding: 0 1em;
  justify-content: space-between;
}
</style>