import styles from "./SignUpFirst.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useNavigate } from "react-router-dom";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { SignUpInput } from "../Signup/Signup";
import { useEffect, useState } from "react";

function SignUpFirst() {
  const naviate = useNavigate();

  const validatePhone: Function = (phone) => {
    return phone
      .match(
        /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/
      );
  };

  const [phone, setPhone] = useState<string>(
    "-를 제외한 전화번호를 입력해주세요."
  );

  const [phoneMsg, setPhoneMsg] = useState<string>("");

  const isPhoneValid: boolean = validatePhone(phone);


  const onChangePhone = (e) => {
    const currentPhone = e.target.value;
    setPhone(currentPhone);
    if (validatePhone(currentPhone)) {
      setPhoneMsg("올바른 전화번호 형식입니다.");
    } else {
      setPhoneMsg("ex) -를 제외한 11자리 전화번호를 입력해주세요.");
    }
  };


  const isAllVaild:boolean = isPhoneValid


  return (
    <div>
    <BackLogoHeader name=" " left="0" fontSize=" " top="0"/>
      <div className={styles.mainContainer}>
        <img className={styles.Logo} src={dondoc} />
        <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0.5rem",marginTop:"0.3rem"}}>회원가입</p>
      </div>
  
        <div className={styles.mainContainerBottom}>
          <div style={{display:"flex",flexDirection:"row",width:"90%",alignItems:"center"}}>
          <SignUpInput type='text' name='전화번호' setting={onChangePhone}/>
            <button className={styles.confirmPass2}>인증하기</button>
          </div>
          <p style={{textAlign:"start",width:"90%"}}>{phoneMsg}</p>
            <SignUpInput type='text' name='이름' setting='onPhone'/>
            
        </div>
      <div className={styles.btnBox} >
        <button className={styles.signUpBtn}>다음</button>
      </div>
    </div>    
  );
}

export default SignUpFirst;
