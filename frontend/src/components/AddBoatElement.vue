<script lang="ts" setup>
import {useStore} from "../services/Store";

const store = useStore();

const submitBoat = () => {
  let name = (document.getElementById("create-boat-form-name") as HTMLInputElement).value;
  let description = (document.getElementById("create-boat-form-description") as HTMLInputElement).value;
  if (name?.length > 0) {
    store.createBoat(name, description);
  }
}
</script>

<template>
  <form id="create-boat-form">
    Create a new boat:
    <input id="create-boat-form-name" placeholder="Name" type="text">
    <input id="create-boat-form-description" placeholder="Description" type="text">
    <button v-on:click.prevent="submitBoat()">Create</button>
  </form>
</template>

<style scoped>
#create-boat-form {
  padding: 1em;
}

#create-boat-form input[type=text] {
  margin-right: 10px;
  height: 2em;
}

#create-boat-form button {
  padding: 5px 8px;
}

#create-boat-form-description {
  width: 300px;
}
</style>