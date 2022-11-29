<template>

  <nav>
    <button @click="$router.push({ name: 'admin' })">admin</button>
    <button @click="$router.push({ name: 'home' })">home</button>
    <button @click="fetchTest()">fetchTest</button>
  </nav>

  <div>
    <router-view></router-view>
  </div>
</template>
<script setup>
import {ref, reactive} from 'vue';

/*const admin = false;
const home = true;*/

async function fetchTest(){
          await fetch('http://localhost:8080/messages')
            .then(response => console.log(response.text()))
        }

</script>
<style>

</style>