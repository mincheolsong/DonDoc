import styles from "./Header.module.css";
import { NavLink } from "react-router-dom";
import { useEffect,useState } from "react";
import { moim } from "../../../api/api";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";

function Header() {

  
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const [notify,setNotify] = useState<boolean>(false);

  useEffect(()=>{
    moim.get('/api/notify/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
  

      if(response.data.response.list){
        setNotify(true)
      }
      
    }).catch((err)=>{
      // console.log(err)
    })
  },[])

  return (
    <div className={styles.header}>
      <img  style={{width:"10rem"}} src={'/src/assets/image/dondoc.svg'} />
      {notify ? <NavLink
      to="/notification">
      <img src={'/src/assets/image/redBell.svg'} />
      </NavLink> : <NavLink
      to="/notification">
      <img src={'/src/assets/image/bell.svg'} />
      </NavLink> }
      
      
    </div>
  );
}

export default Header;
