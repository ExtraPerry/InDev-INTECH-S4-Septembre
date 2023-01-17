<script setup>
import card from '../components/card.vue';
import { ref } from 'vue';

const siteTitle = ref('Titre');
const initialItems = ref([]);

async function getInitialItems() {
    const res = await fetch("http://localhost:8080/getItemsPage", {
      body: {
      'page':0,
      'size':10,
      'sort':'id'
      }
    });
    const finalRes = await res.json();
    initialItems.value = finalRes;
  }

  getInitialItems();
  console.log(initialItems);

</script>

<template>
<div class="home">
  <div class="wall">
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
