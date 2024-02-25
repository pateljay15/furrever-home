import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { ToastContainer } from 'react-toastify'
import "react-toastify/dist/ReactToastify.css";

import {BrowserRouter} from 'react-router-dom'
import { ThemeProvider } from "@material-tailwind/react";


ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ThemeProvider>
    <BrowserRouter>
    <App />
    <ToastContainer position="bottom-center"/>
    </BrowserRouter>
    </ThemeProvider>
  </React.StrictMode>,
)
