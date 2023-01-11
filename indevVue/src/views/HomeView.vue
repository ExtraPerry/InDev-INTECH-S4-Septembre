<script setup>
import card from '../components/card.vue';
import { ref } from 'vue';

const siteTitle = ref('Titre');

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
