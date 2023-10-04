import styles from "./Header.module.css";
import dondocLogo from "../../../assets/image/dondoc.svg"
import bellicon from "../../../assets/image/bell.svg"
import { NavLink } from "react-router-dom";

function Header() {
  return (
    <div className={styles.header}>
      <img src={dondocLogo} />
      
      <NavLink
      to="/notification">
      <img src={bellicon} />
      </NavLink>
      
    </div>
  );
}

export default Header;
