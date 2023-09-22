import styles from "./Nav.module.css";
// import  homeimg from "../../assets/image/home.svg" 
import missionimg from "../../assets/image/mission.svg"
import moimimg from "../../assets/image/moim.svg"
import searchimg from "../../assets/image/search.svg"
import mypageimg from "../../assets/image/mypage.svg"
import { NavLink, useLocation } from "react-router-dom";

interface props{
  dest : string
  name : string
}

function Nav() {
  const location = useLocation();

  return (
    <div className={styles.Nav}>
      <div className={styles.NavBox} >
        <NavItems dest='/' name ="홈" />
        

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


function NavItems(props:props){

  return(
    <NavLink
    to = {props.dest}
    
    >
   <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
    <path d="M15 26.6669C16.4167 27.7169 18.1417 28.3335 20 28.3335C21.8583 28.3335 23.5833 27.7169 25 26.6669" stroke="#000000" stroke-width="2.5" stroke-linecap="round"/>
    <path d="M36.0598 21.5968L35.5948 24.8251C34.7831 30.4718 34.3764 33.2934 32.4181 34.9801C30.4598 36.6668 27.5881 36.6668 21.8431 36.6668H18.1564C12.4114 36.6668 9.53978 36.6668 7.58145 34.9801C5.62311 33.2934 5.21645 30.4718 4.40478 24.8251L3.93978 21.5968C3.30645 17.2018 2.98978 15.0034 3.89145 13.1251C4.79145 11.2468 6.70978 10.1034 10.5448 7.8201L12.8531 6.4451C16.3331 4.3701 18.0764 3.33344 19.9998 3.33344C21.9231 3.33344 23.6648 4.3701 27.1464 6.4451L29.4548 7.8201C33.2881 10.1034 35.2064 11.2468 36.1081 13.1251" stroke="#000000" stroke-width="2.5" stroke-linecap="round"/>
    </svg> 

      {props.name}
    </NavLink>
      // <NavLink
      // to ={props.dest}
      // style={({ isActive, isPending }) => {
      //   return {
      //     fontWeight: isActive ? "bold" : "",
      //     color: isActive ? "black" : "#7E7E7E",
      //     stroke: isActive ? "black" :"#7E7E7E" 
      //   };
      // }}
      // >
      // <img className={styles.NavIcon} src={props.icon} />
      // <p style={{fontSize:'1.4rem'}}>{props.name}</p>
      // </NavLink>
      
  )
}