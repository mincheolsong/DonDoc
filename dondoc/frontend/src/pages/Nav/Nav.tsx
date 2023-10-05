import styles from "./Nav.module.css";
// import  homeimg from "../../assets/image/home.svg" 
import {TbSmartHome} from "react-icons/tb"
import {BiSearch} from "react-icons/bi"
import {MdOutlineJoinFull} from "react-icons/md"
import {LuCheckCheck} from "react-icons/lu"
import {MdPortrait} from "react-icons/md"
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
        <TbSmartHome className={styles.NavIcon} />
        <p className={styles.NavItemsText}>홈</p>
        </div>
        </NavLink>

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={"/search"}>
        <div className={styles.NavItems}>
        <BiSearch className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>찾기</p>
        </div>
        </NavLink>

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "#31C0DE" : "#7E7E7E", textDecorationLine:"none"} }}  to={"/moimhome"}>
        <div className={styles.NavItems}>
        <MdOutlineJoinFull className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>돈독계좌</p>
        </div>
        </NavLink>
        

        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={`/mission`}>
        <div className={styles.NavItems}>
        <LuCheckCheck className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>나의미션</p>
        </div>
        </NavLink>


        <NavLink style={({ isActive, isPending })=>{ return{ color: isActive ? "black" : "#7E7E7E", textDecorationLine:"none"} }}  to={`/mypage/${userInfo.phoneNumber}`}>
        <div className={styles.NavItems}>
        <MdPortrait className={styles.NavIcon}/>
        <p className={styles.NavItemsText}>마이페이지</p>
        </div>
        </NavLink>
        
      </div>
    </div>
  );
}

export default Nav;
