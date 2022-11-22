<template>

  <nav>
    <button @click="displayAdmin()">admin</button>
    <button @click="$router.push({ name: 'admin' })">adminn</button>
    <button @click="displayHome()">Home</button>
    <button @click="fetchTest()">fetchTest</button>


  </nav>
  <!-- <AdminView v-if="admin" />
  <HomeView v-if="home" /> -->

<div>je suis une div
  <div>
    <router-view></router-view>
  </div>
</div>
</template>
<script>
import AdminView from './views/AdminView.vue';
import HomeView from './views/HomeView.vue';


  export default {
      components: {
        AdminView,
        HomeView
      },
      data() {
          return {
            admin: false,
            home: true
          }
      },
      methods: {
        displayAdmin(){
          this.admin = !this.admin;
        },
        displayHome(){
          this.home = !this.home;
        },
        async fetchTest(){
          await fetch('http://localhost:8080/messages')
            .then(response => console.log(response.text()))
        }
      }
  }
</script>
<style>

</style>