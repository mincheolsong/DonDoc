import styles from "./BackLogoHeader.module.css";
import { useNavigate } from "react-router-dom";
import {IoChevronBack} from 'react-icons/io5'
import { IconContext } from "react-icons";

interface BackLogoHeaderType{
  left : string;
  name : string;
  fontSize: string;
  top :string; 
}



export function BackLogoHeader(props:BackLogoHeaderType){
  const naviate = useNavigate();
  return(
    <div style={{display:"flex",justifyContent:"start", }}>
    <IconContext.Provider  value={{className: styles.backLogo}}>
      <div onClick={()=>{
        naviate(-1)
      }} >
      <IoChevronBack/>
      </div>
    </IconContext.Provider>
      <p style={{fontSize:props.fontSize,marginLeft:props.left,marginTop:props.top,marginBottom:'0px',  fontWeight:"bold"}}>{props.name}</p>
    </div>
  )
}
export default BackLogoHeader;
