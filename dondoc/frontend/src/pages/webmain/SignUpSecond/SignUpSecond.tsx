import styles from "./SignUpSecond.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'
import { useLocation, useNavigate } from "react-router-dom";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";
import { SignUpInput1 } from "../SignUpFirst/SignUpFirst";
import {useState} from "react"
import { moim } from "../../../api/api";
import { useDispatch } from "react-redux";
import { loginUser } from "../../../store/slice/userSlice";
import OneBtnModal from "../../toolBox/OneBtnModal";

interface PassBox{
  innerText:string;
  change(e:React.ChangeEvent<HTMLInputElement>):void;
  inner:boolean;
  helpMsg:string;
}

function SignUpSecond() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {state} = useLocation();
  const phone = state.phone 
  const name = state.name
  const [errText,setErrText] = useState<string>('')
  const [errModal,setErrModal] = useState<boolean>(false)

  //유효성검사
  const validatePwd = (password:string) => {
    return password
      .toLowerCase()
      .match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/);
  };
  
  const validateNickname = (nickname:string) => {
    return nickname
      .match(/^[A-Za-z0-9가-힣]{3,20}$/);
  };
  //

  // 스테이트에 저장된는곳 
  const [passWord, setPassWord] = useState<string>("");
  const [nickName, setNickName] = useState<string>("");
  const [conPassWord, setConPassWord] = useState<boolean>(false);

//
// helpMag 랑 글씨 색깔
const [passMsg, setPassMsg] = useState<string>("");
const [conPassMsg, setConPassMsg] = useState<string>("");
const [NickMsg, setNickMsg] = useState<string>("");


const [passInnerColor,setPassInnerColor] = useState<boolean>(false);
const [conPassInnerColor,setConPassInnerColor] = useState<boolean>(false);
const [nickInnerColor,setNickInnerColor] = useState<boolean>(false);

//
// onchange 함수부
const onChangePass = (e:React.ChangeEvent<HTMLInputElement>) => {
  const currentPass = e.target.value;
  setPassWord(currentPass);
  if(!currentPass){
    setPassMsg("")
  }{
    if (validatePwd(currentPass)) {
      setPassMsg("올바른 비밀번호 입니다.");
      setPassInnerColor(true)
    } else {
      setPassMsg("영문 + 숫자 + 특수문자를 포함한 8자리 이상");
      setPassInnerColor(false)
    }
  }
  
};

const onChangeConPass = (e:React.ChangeEvent<HTMLInputElement>) => {
  const currentConPass = e.target.value;
  if(!currentConPass){
    setConPassMsg("")
  }else{
    if (passWord == currentConPass){
    setConPassMsg("비밀번호가 일치합니다.")
    setConPassWord(true)
    setConPassInnerColor(true)
  } 
   else{
    setConPassMsg("비밀번호가 일치하지 않습니다.");
    setConPassWord(false)
    setConPassInnerColor(false)
  }}
  
};

const onChangeNick = (e:React.ChangeEvent<HTMLInputElement>) => {
  const currentNick = e.target.value;
  setNickName(currentNick)
  if (validateNickname(currentNick)){
    setNickMsg("닉네임에 적합합니다.")
    setNickInnerColor(true)
  } else {
    if(!currentNick){
      setNickMsg("")
    }else
    setNickMsg("특수문자를 제외한 3자 이상 20자 이내")
    setNickInnerColor(false)
  }
};

//



const isPassValid = validatePwd(passWord);
const isConPassValid = conPassWord;
const isNickValid = validateNickname(nickName);
const isAllVaild = isPassValid && isConPassValid && isNickValid


const userSetting = {
  certification: true,
  name : name,
  nickName:nickName,
  password:passWord,
  phoneNumber : phone,
}


const SignInPost = ()=>{
  moim.post("/api/user/signin",userSetting)
  .then((response)=>{
    if(response.data.success == true){
      const userUpdate = {
        password:userSetting.password,
        phoneNumber:userSetting.phoneNumber,
        accessToken:response.data.response.accessToken
      }
      dispatch(loginUser(userUpdate))

    }else{
      setErrText(response.data.error.message)
    }
    
  })
  .catch((error)=>{
    console.log(error)
  })
}




const SignUpPost = ()=>{
  moim.post("/api/user/signup",userSetting)
  .then((response)=>{
    if(response.data.success == true){
      console.log(response)
    SignInPost();
    navigate('/signupTemp')
    }else{
      setErrText(response.data.error.message)
      setErrModal(!errModal)
    }
  })
  .catch((error)=>{
    console.log(error)
  })
}


interface OneBtnModal{
  width : string;
  height: string;
  titleText: string;
  title: boolean;
  titleFont:string;
  contentFont: string;
  contentText: string;
  btncolor : string;
  btnTextColor:string;
  btnText:string;
  callback:void;
}

const closeModal = ()=>{
  setErrModal(!errModal)
}


  return (
    <div>
    {errModal ? <OneBtnModal width="80vw" height="50vh" titleText="" title="false" contentText={errText}  contentFont="1.5rem" btncolor="white" btnTextColor="black" btnText="닫기" callback={closeModal}  /> : ""}
    <BackLogoHeader name=" " left="0" fontSize=" " top="0"/>
      <div className={styles.mainContainer}>
        <img className={styles.Logo} src={dondoc} />
        <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0.5rem",marginTop:"0.3rem"}}>회원가입</p>
      </div>
  
        <div className={styles.mainContainerBottom}>
  
          <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
          <PassBox innerText="비밀번호" change={onChangePass} inner={passInnerColor} helpMsg={passMsg} />
          <PassBox innerText="비밀번호확인" change={onChangeConPass} inner={conPassInnerColor} helpMsg={conPassMsg} />
          <SignUpInput1 type="text" inner={nickInnerColor} innerText="닉네임" change={onChangeNick} helpMsg={NickMsg} />
          </div>
          
            
        </div>
      <div className={styles.btnBox} >
        
        {isAllVaild ? <button className={styles.signUpBtn} onClick={SignUpPost} >회원가입</button> :
        <button className={styles.signUpBtnDis} disabled>회원가입</button>}
        
      </div>
    </div>    
  );
}

export default SignUpSecond;


export function PassBox(props:PassBox){
  return(
    <div>
      <input type="password" placeholder={props.innerText} className={styles.IdBox} onChange={props.change}/>
      <p style={{margin:"0" , color : props.inner ? "green" : "#FF001F ", fontWeight:"bold"}}>{props.helpMsg}</p>
    </div>
    
  )
}