import styles from "./Signin.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useState } from "react";
import { useNavigate } from 'react-router-dom';


interface SignProps {
  name: string;
  setting: any;
  type:string;
}


function Signin() {
  const [id,setId] = useState<string>('')
  const [password,setPassword] = useState<string>('')
  const navigate = useNavigate();

  const onId = (e:React.ChangeEvent<HTMLInputElement>) =>{
    setId(e.target.value)
    console.log(id)
  };
  const onPass = (e:React.ChangeEvent<HTMLInputElement>) =>{
    setPassword(e.target.value)
    console.log(password)
  }
  

  return (
      <div>
        <div className={styles.containerTop}>
          <img className={styles.Logo} src={dondoc} />
              <div className={styles.inputBox} >
              <SignInInput name='아이디를 입력해주세요' type='text' setting={onId}/>
              <SignInInput name='비밀번호를 입력하세요' type='password' setting={onPass}/>
              </div>
          <button className={styles.loginBtn}>로그인</button>
        </div>
        <hr style={{width:"90%",color:"#D1CCCF"}}/>
        <div className={styles.containerMid}>
          <div style={{width:'40%',display:"flex", justifyContent:"end"}}><p onClick={()=>{navigate('/signup')}} style={{fontWeight:"bold", margin:"1rem", marginRight:"2rem" }}>회원가입</p></div>
            <div className={styles.signUpbtn}></div>
          <div style={{width:'40%',display:"flex", justifyContent:"start"}}><p onClick={()=>{navigate('/changepassword')}} style={{fontWeight:"bold" ,margin:"1rem", marginLeft:"2rem"}}>비밀번호 찾기</p></div>
        </div>
      </div>
  );
}

export default Signin;


function SignInInput(props:SignProps){
  return(
    <input className={styles.IdBox} type={props.type} placeholder={props.name} onChange={props.setting}/>
  )
}