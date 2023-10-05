import styles from "./SignUpFirst.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useNavigate } from "react-router-dom";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { useEffect, useState } from "react";
import { moim } from "../../../api/api";

function SignUpFirst() {
  const naviate = useNavigate();


  // 유효성검사
  const validatePhone: Function = (phone:string) => {
    return phone
      .match(
        /^010\d{4}\d{4}$/
      );
  };


// 


// 스테이트에 저장된는곳 
  const [phone, setPhone] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [certificationBtn,setCertificationBtn] = useState<boolean>(false);
  const [smsInput,setSmsInput] = useState<string>('');
  const [smsResponse,setSmsResponse] = useState<string>();
//
  const [phoneMsg, setPhoneMsg] = useState<string>("");
  const [smsMsg,setSmsMsg] = useState<string>('');
  const [innerColor,setInnerColor] = useState<boolean>(false);
  const [smsColor,setSmsColor] = useState<boolean>(false);
  
  const onChangePhone = (e:React.ChangeEvent<HTMLInputElement>) => {
    const currentPhone = e.target.value;
    setPhone(currentPhone);

    if (validatePhone(currentPhone)) {
      setPhoneMsg("올바른 전화번호 형식입니다.");
      setInnerColor(true)
    } else {

      if(!currentPhone){
        setPhoneMsg("")
      }else
      setPhoneMsg("ex) -를 제외한 11자리 전화번호를 입력해주세요.");
      setInnerColor(false)
    }
  };

  const onChangeName = (e:React.ChangeEvent<HTMLInputElement>) => {
    const currentName = e.target.value;
    setName(currentName);
  };


  const onChangeCer = (e:React.ChangeEvent<HTMLInputElement>) => {
    const currentSms = e.target.value;
    setSmsInput(currentSms);
  };

  const cerBtn = ()=>{
    if (smsInput == smsResponse) {
      setSmsMsg("인증이 완료되었습니다.");
      setSmsColor(true)
    } else {
      setSmsMsg("인증번호를 다시 입력해주세요.");
      setSmsColor(false)
    }
  }

  

  const isPhoneValid: boolean = validatePhone(phone) && smsColor;



  return (
    <div>
    <BackLogoHeader name=" " left="0" fontSize=" " top="0"/>
        <div className={styles.mainContainer}>
          <img className={styles.Logo} src={dondoc} />
          <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0.5rem",marginTop:"0.3rem"}}>회원가입</p>
        </div>
  

        <div className={styles.mainContainerBottom}>
          <div style={{width:"80%"}}>
          <SignUpInput1 type='text' innerText='이름' change={onChangeName} helpMsg={""} inner={true}/>
          </div>
          <div style={{display:"flex",flexDirection:"row",width:"80%",alignItems:"start"}}>
         
          {certificationBtn ?  
            <div style={{display:"flex",width:"100%",flexDirection:"column"}}>
            <input type="text" value={phone} className={styles.IdBoxdis} disabled/>
            <p style={{ margin:"0", color : innerColor ? "green" : "#FF001F ", fontWeight:"bold" }}>{phoneMsg}</p>
          </div>
          :  <SignUpInput1 type='text' innerText='전화번호' change={onChangePhone} helpMsg={phoneMsg} inner={innerColor}/>}
          {certificationBtn ? <button className={styles.confirmPass2dis}>인증하기</button> : <button onClick={()=>{
            if(validatePhone(phone)){setCertificationBtn(true)
              moim.post(`/api/user/sms/signup/${phone}`,null)
              .then((response)=>{
                console.log(response)
                const resNum = response.data.response.certificationNumber
                setSmsResponse(resNum)
              }).catch((err)=>{
                console.log(err)
              })}
              else{
                setPhoneMsg("ex) -를 제외한 11자리 전화번호를 입력해주세요.")
              }
            

          }} className={styles.confirmPass2}>인증하기</button>}
          
        </div>
        </div>
          {certificationBtn ? 
          <div>
            <input onChange={onChangeCer} placeholder="인증번호를 입력해주세요." className={styles.cerInput} type="text" name="" id="" /> <button style={{width:"5.5rem",border:"0", backgroundColor:"#0D6EFD",color:"white", borderRadius:"0.5rem",height:"3.8rem",marginLeft:"3%"}} onClick={cerBtn}>확인</button>
            <p style={{marginLeft:"10%",color:smsColor ?  "green" : "#FF001F " ,fontWeight:"bold"}}>{smsMsg}</p>
          </div>
          
          
          : ""}


      <div className={styles.btnBox} >
        {isPhoneValid ? <button className={styles.signUpBtn} onClick={()=>{
          naviate('/signupsecond',{state:{phone:phone,name:name}})}}>다음</button> : <button className={styles.signUpBtnDis} disabled>다음</button>}
        
        
      </div>
    </div>    
  );
}

export default SignUpFirst;

interface SignUpInput{
  type:string;
  innerText:string;
  change(e:React.ChangeEvent<HTMLInputElement>):void;
  inner:boolean;
  helpMsg:string;
}


export function SignUpInput1(props:SignUpInput){
  return(
    <div style={{display:"flex",width:"100%",flexDirection:"column"}}>
      <input type={props.type} placeholder={props.innerText} className={styles.IdBox} onChange={props.change}
      />
      <p style={{ margin:"0", color : props.inner ? "green" : "#FF001F ", fontWeight:"bold" }}>{props.helpMsg}</p>
    </div>
    
  )
}

