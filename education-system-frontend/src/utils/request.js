import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:9099',
    timeout: 5000,
    withCredentials: true
})

export default request 