import styles from "./SignUpSecond.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useNavigate } from "react-router-dom";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { SignUpInput } from "../Signup/Signup";


function SignUpSecond() {
  const naviate = useNavigate();
  return (
    <div>
    <BackLogoHeader name=" " left="0" fontSize=" " top="0"/>
      <div className={styles.mainContainer}>
        <img className={styles.Logo} src={dondoc} />
        <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0.5rem",marginTop:"0.3rem"}}>회원가입</p>
      </div>
  
        <div className={styles.mainContainerBottom}>
            <SignUpInput type='text' name='닉네임' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호 확인' setting='onPhone'/>
            
        </div>
      <div className={styles.btnBox} >
        <button className={styles.signUpBtn}>회원가입</button>
      </div>
    </div>    
  );
};

export default SignUpSecond;
