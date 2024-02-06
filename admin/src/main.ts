import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import { aliases, mdi } from 'vuetify/iconsets/mdi'
import { md3 } from 'vuetify/blueprints'
import '@mdi/font/css/materialdesignicons.css'

import PrimeVue from 'primevue/config'
import 'primevue/resources/themes/aura-light-green/theme.css'

import { VueFire, VueFireAuth } from 'vuefire'
import { firebaseApp } from '@/firebase'

import axios from 'axios'

const app = createApp(App)

const BASE_URL = import.meta.env.DEV ? 'http://localhost:8080' : 'https://api.example.com'
export const http = axios.create({
  baseURL: BASE_URL
})

app
  .use(createPinia())
  .use(PrimeVue, { ripple: true })
  .use(
    createVuetify({
      blueprint: md3,
      theme: {
        defaultTheme: 'dark'
      },
      icons: {
        defaultSet: 'mdi',
        aliases,
        sets: {
          mdi
        }
      }
    })
  )
  .use(VueFire, {
    firebaseApp,
    modules: [VueFireAuth()]
  })
  .use(router)

  .mount('#app')
