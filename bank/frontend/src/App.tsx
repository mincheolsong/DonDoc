// import { useState } from 'react'
// import { BrowserRouter,Route, Link, Switch } from "react-router-dom";
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import {BrowserRouter as Router} from 'react-router-dom'
import styles from './App.module.css'
import RouteFile from "./Router"
import NavBar from './pages/NavBar'

function App() {
  // const [count, setCount] = useState(0)

  return (
    <Router>
      <div className={styles.container}>
        <div className={styles.content}>
          <NavBar />
          <RouteFile />
        </div>
      </div>
    </Router>
  )
}

export default App
