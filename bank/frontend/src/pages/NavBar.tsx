import styles from './NavBar.module.css'
import { Link, useNavigate } from "react-router-dom";
// import { DDBANK } from '../assets/images';


function NavBar() {

  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div className={styles.links}>

        {/* <Link className={styles.linktext} to="/login">뒤로</Link> */}
        <a className={styles.linktext} onClick={() => navigate(-1)}>뒤로</a>
        <Link className={styles.linktext} to="/">DDBANK</Link>
        <Link className={styles.linktext} to="/account">계좌개설</Link>
        <Link className={styles.linktext} to="/myapi">은행업무</Link>

      </div>
    </div>
  )
}

export default NavBar