import styles from "./Signup.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useNavigate } from "react-router-dom";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";

interface SignProps {
  name: string;
  setting: any;
  type:string;
}




function Signup() {
  const naviate = useNavigate();
  
  return (
    <div>
    <BackLogoHeader/>
    
      <div className={styles.mainContainer}>
        <img className={styles.Logo} src={dondoc} />
      </div>
  
        <div className={styles.mainContainerBottom}>
            <SignUpInput type='text' name='전화번호' setting='onPhone'/>
            <SignUpInput type='text' name='이름' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호 확인' setting='onPhone'/>
            <SignUpInput type='text' name='이메일' setting='onPhone'/>
            <SignUpInput type='text' name='닉네임' setting='onPhone'/>
        </div>
      <div className={styles.btnBox} >
        <button className={styles.signUpBtn}>회원가입</button>
      </div>
    </div>    
  );
}


function SignUpInput(props:SignProps){
  return(
    <input className={styles.IdBox} type={props.type} placeholder={props.name} onChange={props.setting}/>
  )
}
export default Signup;


// export function BackLogo(props){
//   const naviate = useNavigate();
//   return(
//     <div style={{display:"flex",justifyContent:"start"}}>
//     <IconContext.Provider  value={{className: styles.backLogo}}>
//       <div onClick={()=>{
//         naviate(-1)
//       }} >
//       <IoChevronBack/>
//       </div>
//     </IconContext.Provider>
//       <p style={{fontSize:props.fontSize,marginLeft:props.left,marginTop:props.top, margin:"0",  fontWeight:"bold"}}>{props.name}</p>
//     </div>
//   )
// }