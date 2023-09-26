import styles from "./Signin.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import { moim } from "../../../api/api";
import { loginUser } from "../../../store/slice/userSlice";
import { useDispatch,useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";

interface SignProps {
  name: string;
  setting: any;
  type:string;
}


function Signin() {
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const dispatch = useDispatch();
  const [id,setId] = useState<string>('')
  const [password,setPassword] = useState<string>('')
  const navigate = useNavigate();
  const [errText,setErrText] = useState<string>('')
  const onId = (e:React.ChangeEvent<HTMLInputElement>) =>{
    const currentId = e.target.value
    setId(currentId)
  };
  const onPass = (e:React.ChangeEvent<HTMLInputElement>) =>{
    const currentPass = e.target.value
    setPassword(currentPass)
  }
  
  const userSetting = {
    password:password,
    phoneNumber:id,
  }

  const SignInPost = ()=>{
    moim.post("/api/user/signin",userSetting)
    .then((response)=>{
      if(response.data.success == true){
        const userUpdate = {
          password:password,
          phoneNumber:id,
          accessToken:response.data.response.accessToken
        }
        dispatch(loginUser(userUpdate))
        navigate('/')
      }else{
        setErrText(response.data.error.message)
      }
      
    })
    .catch((error)=>{
      console.log(error)
    })
  }


  

  return (
      <div>
        
        <div className={styles.containerTop}>
          <img className={styles.Logo} src={dondoc} />
              <div className={styles.inputBox} >
              <SignInInput name='전화번호를 입력해주세요' type='text' setting={onId}/>
              <SignInInput name='비밀번호를 입력하세요' type='password' setting={onPass}/>
              
              </div>
              <p style={{color:"red",fontWeight:"bold"}}>{errText}</p>
          <button className={styles.loginBtn} 
          onClick={SignInPost}>로그인</button>
        </div>

        <hr style={{width:"90%",color:"#D1CCCF"}}/>
        <div className={styles.containerMid}>
          <div style={{width:'40%',display:"flex", justifyContent:"end"}}><p onClick={()=>{navigate('/signupfirst')}} style={{fontWeight:"bold", margin:"1rem", marginRight:"2rem" }}>회원가입</p></div>
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