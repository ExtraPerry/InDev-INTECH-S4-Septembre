const fetch = (...args) => import('node-fetch').then(({ default: fetch }) => fetch(...args));
// import request from ("./request");

// get( 'http://localhost:8080/getItemsPage', { page: 0, size: 10, sort: "id" } )
// .then( response => {
//     console.log(response);
// } );


export function getInitialItems(){
//     request('http://localhost:8080/getItemsPage', { page: 0, size: 10, sort: "id" }, method = 'GET')
    fetch('http://localhost:8080/getItemsPage?page=0&size=10&sort=id', { method: 'GET' })
    .then((response) => response.json())
    .then((json) => { console.log(json) })
} 
