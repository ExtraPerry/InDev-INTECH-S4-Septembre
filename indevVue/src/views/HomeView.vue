<script setup>
import card from '../components/card.vue';
import { ref } from 'vue';
// import { getInitialItems } from "../../../bot/Functions/getInitialItems";

const siteTitle = ref('Titre');
const initialItems = ref([]);

async function getInitialItems() {
  const url = (
    'http://localhost:8080/getItemsPage?page=0&size=10&sort=id'
  );

  const result = await fetch(url)
    .then(response => response.json());

  console.log('Fetched from: ' + url);
  console.log(result);
}

</script>

<template>
<div class="home">
  <div class="wall">
    <button @click="getInitialItems">getItems</button>
    <card :siteTitle= siteTitle />
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    <card></card>
    
  </div>
</div>
 
</template>
