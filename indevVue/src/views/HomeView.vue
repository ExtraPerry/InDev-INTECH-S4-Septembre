<script setup>
import card from '../components/card.vue';
import { ref, onMounted, watch } from 'vue';
import { useInitialItems } from "../stores/initialItems";
const siteTitle = ref('Titre');
const initialItems = useInitialItems();
let items = ref('');

onMounted(async () => {
  await initialItems.getInitialItems();
  items = initialItems.items

  console.log('SSSS',items);
})
watch(items, async (newQuestion, oldQuestion) => {
  console.log('SSSS',newQuestion,  oldQuestion);

})


</script>

<template>
<div class="home">
  <div class="wall">
    QQQQ {{ initialItems.items }}
    <button @click="initialItems.getInitialItems">getItems</button>
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
