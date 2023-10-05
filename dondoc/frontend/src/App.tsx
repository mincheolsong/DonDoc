import {BrowserRouter as Router} from 'react-router-dom'
// import styles from './App.module.css'
import RouteFile from "./Router"
// import NavBar from './pages/NavBar'

function App() {
  // const [count, setCount] = useState(0)

  return (
    <Router>
          {/* <NavBar /> */}
          <RouteFile />
    </Router>
  )
}

export default App
