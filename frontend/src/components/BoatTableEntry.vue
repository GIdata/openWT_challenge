<script lang="ts" setup>
import Boat from "../domain/Boat";
import {useStore} from "../services/Store";

defineProps<{
  boat: Boat
}>()

const store = useStore()

</script>

<template>
  <div class="boat-entry" v-on:click="store.selectBoat(boat)">
    <span class="boat-table-col0">{{ boat.id }}</span>
    <h3 class="boat-table-col1">{{ boat.name }}</h3>
    <span class="boat-table-col2">{{ boat.description }}</span>
    <div class="boat-table-col3 boat-edit-buttons">
      <button id="delete-button" v-on:click.stop="store.deleteBoat(boat)">X</button>
      <button id="edit-button" v-on:click.stop="store.editBoat(boat)">Edit</button>
    </div>
  </div>
</template>

<style scoped>
h1 {
  margin: 0.5em 0;
}

.boat-entry {
  margin-top: 5px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.boat-edit-buttons button {
  float: right;
  margin-left: 5px;
  background: none;
  border: none;
  padding: 4px 8px;
}

#delete-button:hover {
  background-color: var(--warning-color);
}

#edit-button:hover {
  background-color: var(--background-color-darker);
}


</style>