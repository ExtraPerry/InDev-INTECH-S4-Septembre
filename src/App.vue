<script setup lang="ts">

import { ref } from 'vue';
import HomeViewVue from './views/HomeView.vue';
import AboutViewVue from './views/AboutView.vue';
import ContactViewVue from './views/ContactView.vue';



const home = ref(true);
const contact = ref(false);
const about = ref(false);


function displayHome() {
  home.value = true
  contact.value = false
  about.value = false
}

function displayContact() {
  home.value = false
  contact.value = true
  about.value = false
  /*contact.value = !contact.value*/
}

function displayAbout() {
  home.value = false
  contact.value = false
  about.value = true
  /* about.value = !about.value*/
}

function fetchTest() {
  fetch('http://localhost:8080/messages')
    .then(response => console.log(response.text()))
}

</script>

<template>

  <!-- Menu de navigation-->
  <header>
    <div class="topnav">
      <button class="btnMenu" @click="displayHome">Home</button> <!-- <a class="active" href="#home">Home</a>-->
      <button class="btnMenu" @click="displayContact">Contact</button> <!-- <a href="#contact">Contact</a>-->
      <button class="btnMenu" @click="displayAbout">About</button> <!-- <a href="#about">About</a> -->
      <button class="btnMenu" @click="fetchTest">fetchTest</button>
    </div>
  </header>

 

  <HomeViewVue v-if="home" />
  <ContactViewVue v-if="contact" />
  <AboutViewVue v-if="about" />

  <div class="separateur"></div>

  <!-- Pied de page -->
  <footer>
    <p>
      Copyright &copy; In'dev 2022-2023 - All right Reserved
    </p>
  </footer>
</template>

<style>
* {
  box-sizing: border-box;
  /*align-items: center;*/
}

body {
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

header {
  background-color: #282A35;

}

.btnMenu {
  text-align: center;
  padding: 1em;
}

div#content div {
  display: inline-block;
  width: 48%;
  vertical-align: top;
  margin: 10px;
}

div#content {
  background-color: #E8E8E8;
}

.home {
  display: flex;
  flex-direction: column;
  place-items: center;
  width: 100%;
  height: 100%;
}

.wall {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  width: 90%;
  height: 30em;
  background-color: #292b2f;
  justify-content: space-between;
  margin-top: 5%;
  border-radius: 15px;
}

.cards {
  display: flex;
  background-color: #36393f;
  width: 22em;
  height: 9em;
  margin: 0.5em;
  border-radius: 10px;
}

.sBar {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 45%;
  vertical-align: top;

}


.searchBar {
  width: 40%;
  height: 10%;
  border: 1px black solid;
  border-radius: 25px;
  margin-top: 0.25rem;
  border-width: 2px;
  border-radius: 9999px;
  border-style: solid;

}

.btnSearch {
  padding-left: 5%;
}


.topnav {
  overflow: hidden;
  background-color: cadetblue;
  padding-bottom: 0.5%;
  padding-top: 0.5%;
  padding-left: 1%;
  padding-right: 1%;
}

.topnav a {
  float: left;
  display: block;
  color: black;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.topnav a:hover {
  background-color: #ddd;
  color: black;
}

.topnav a.active {
  background-color: #2196F3;
  color: white;
}

.topnav input[type=text] {
  float: right;
  padding: 6px;
  margin-top: 8px;
  margin-right: 16px;
  border: none;
  font-size: 17px;
}

.separateur {
  width: 100%;
  height: 6%;
  background-color: black;
}

.text {
  text-indent: 50px;
  text-align: center;
  letter-spacing: 3px;
  padding-left: 5%;
  padding-right: 5%;
  padding-top: 15%;
}

.fullscreen {
  height: 100%;
  width: 100%;

}



footer {
  position: relative;
  bottom: 0%;
  background-color: hsla(50, 33%, 25%, .75);
  text-align: center;
  display: flex;
  justify-content: center;
  width: 100%;
  padding: 5px;

}
</style>