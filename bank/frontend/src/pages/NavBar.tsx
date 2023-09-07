import styles from './NavBar.module.css'
import { Link } from "react-router-dom";


function NavBar() {

  return (
    <div className={styles.container}>
      <div className={styles.links}>

        <Link className={styles.linktext} to="/">DDBank</Link>
        <Link className={styles.linktext} to="/login">로그인</Link>
        <Link className={styles.linktext} to="/account">은행</Link>
        <Link className={styles.linktext} to="/myapi">내API</Link>

      </div>
    </div>
  )
}

export default NavBar