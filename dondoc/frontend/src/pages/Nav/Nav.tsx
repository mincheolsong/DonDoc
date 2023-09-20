import styles from "./Nav.module.css";
import homeimg from "../../assets/image/home.svg"
import missionimg from "../../assets/image/mission.svg"
import moimimg from "../../assets/image/moim.svg"
import searchimg from "../../assets/image/search.svg"
import mypageimg from "../../assets/image/mypage.svg"
import { NavLink, useLocation } from "react-router-dom";

function Nav() {
  const location = useLocation();

  return (
    <div className={styles.Nav}>
      <div className={styles.NavBox} >
        <NavItems dest='/' name ="홈" icon ={homeimg} />

        <div style={{textAlign:"center",}}>
        <img className={styles.NavIcon} src={homeimg}  />
        <p>홈</p>
        </div>

        <div>
        <img className={styles.NavIcon} src={searchimg}  />
        <p>홈</p>
        </div>

        <div>
        <img className={styles.NavIcon}src={moimimg}  />
        <p>돈독계좌</p>
        </div>
        
        <div>
        <img className={styles.NavIcon} src={missionimg}  />
        <p>나의 미션</p>
        </div>

        <div>
        <img className={styles.NavIcon} src={mypageimg}  />
        <p>마이페이지</p>
        </div>
        
      </div>
    </div>
  );
}

export default Nav;


function NavItems(props){

  return(
      <NavLink
      to ={props.dest}
      style={({ isActive, isPending }) => {
        return {
          fontWeight: isActive ? "bold" : "",
          color: isActive ? "black" : "#7E7E7E",
          fill: isActive ? "black" :"#7E7E7E" 
        };
      }}
      >
      <img className={styles.NavIcon} src={props.icon} />
      <p style={{fontSize:'1.4rem'}}>{props.name}</p>
      </NavLink>
      
  )
}