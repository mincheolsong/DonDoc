import styles from './NavBar.module.css'
import { Link } from "react-router-dom";
// import { DDBANK } from '../assets/images';


function NavBar() {

  return (
    <div className={styles.container}>
      <div className={styles.links}>

        <Link className={styles.linktext} to="/">DDBANK</Link>
        <Link className={styles.linktext} to="/login">로그인</Link>
        <Link className={styles.linktext} to="/account">API소개</Link>
        <Link className={styles.linktext} to="/myapi">은행업무</Link>

      </div>
    </div>
  )
}

export default NavBar