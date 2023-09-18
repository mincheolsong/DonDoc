import {BrowserRouter as Router} from 'react-router-dom'
import styles from './App.module.css'
import RouteFile from "./router"
// import NavBar from './pages/NavBar'

function App() {
  // const [count, setCount] = useState(0)

  return (
    <Router>
      <div className={styles.container}>
        <div className={styles.content}>
          {/* <NavBar /> */}
          <RouteFile />
        </div>
      </div>
    </Router>
  )
}

export default App
