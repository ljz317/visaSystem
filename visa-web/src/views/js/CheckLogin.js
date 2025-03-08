import {onMounted} from "vue";

export const checkBefore=()=>{
    if(!localStorage.user){
        alert("先にログインしてください")
        document.location.href="/login"
    }
}
