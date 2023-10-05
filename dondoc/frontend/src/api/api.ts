import axios from "axios";

export const bank = axios.create({
    baseURL:"http://j9d108.p.ssafy.io:9090",
});

export const moim = axios.create({
    baseURL:"http://j9d108.p.ssafy.io:9191"
})
