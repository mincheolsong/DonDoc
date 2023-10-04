import styles from "./Nav.module.css";
// import  homeimg from "../../assets/image/home.svg" 
import {RiHomeSmile2Line} from "react-icons/ri"
import {BiSearch} from "react-icons/bi"
import {MdOutlineSwitchAccount,MdOutlinePlaylistAddCheck} from "react-icons/md"
import {CgProfile} from "react-icons/cg"
import { NavLink, useLocation } from "react-router-dom";
import { UserType } from "../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";

function Nav() {
  const location = useLocation();
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  return (
    <div className={styles.Nav}>
      <div className={styles.NavBox} >
        
        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }} to={"/"}>
        <div className={styles.NavItems}>
        <RiHomeSmile2Line className={styles.NavIcon} />
        <p className={styles.NavItemsText}>홈</p>
        </div>
        </NavLink>

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={"/search"}>
        <div className={styles.NavItems}>
        <BiSearch className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>찾기</p>
        </div>
        </NavLink>

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={"/moimhome"}>
        <div className={styles.NavItems}>
        <MdOutlineSwitchAccount className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>돈독계좌</p>
        </div>
        </NavLink>
        

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={`/mission/${userInfo.userId}`}>
        <div className={styles.NavItems}>
        <MdOutlinePlaylistAddCheck className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>나의미션</p>
        </div>
        </NavLink>


        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={`/mypage/${userInfo.phoneNumber}`}>
        <div className={styles.NavItems}>
        <CgProfile className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>마이페이지</p>
        </div>
        </NavLink>
        
      </div>
    </div>
  );
}

export default Nav;
