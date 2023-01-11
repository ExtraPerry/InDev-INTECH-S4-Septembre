<script setup>

    const props = defineProps({
        siteTitle: String,
        title: String,
        description: String,
        tags: Array
    })

</script>

<template>
    <div class="card">
        <div class="imgContainer">
            <img src="../../ressources/Notion-logo.svg.png" alt="">
        </div>
        <div class="infoContainer">
        <p class="siteTitle">{{siteTitle}}</p>
        <p class="title">{{title}}</p>
        <p class="description">{{description}}</p>
        <p class="tags">{{tags}}</p>
        </div>
      
    </div>

</template>

<style scoped>

    @import url('https://fonts.googleapis.com/css2?family=Berkshire+Swash&family=League+Gothic&family=Lobster&family=Prompt:wght@300&family=Roboto:wght@100&display=swap');



   .card{
    display: flex;
    flex-direction: row;
    align-items: center;
    text-indent: 0.1em;
    background-color: #36393f ;
    width: 22em;
    height: 9em;
    margin: 0.5em;
    border-radius: 10px;

    }
    .card .siteTitle {
        font-weight: bold;
        /* margin: 1em; */
    }
    .card p {
        margin: 0.1em;
    }
    .card .description {
        font-weight: 100;
    }
    .card img {
        width: 5em;
        height: 5em;
    }
    .imgContainer {
        display: flex;
        align-items: center;
        margin: 1em;
    }

</style>