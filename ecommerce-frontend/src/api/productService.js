import axios from 'axios';

// Create an axios instance with default configurations
const api = axios.create({
    baseURL: 'http://localhost:8080/api/',
    headers:{
"Content-Type":'application/json',
    }
});


export const productService = {
    searchProduct: async (query)=>{
        if(!query.trim()) return [];
        const response = await api.get('products',{
            params : {searchName : query}
        });
      return response.data;
    }
};