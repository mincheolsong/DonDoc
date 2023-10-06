import styles from "./ChangePassword.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import {useState} from "react"
import { PassBox } from "../../webmain/SignUpSecond/SignUpSecond";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { moim } from "../../../api/api";
import OneBtnModal from "../../toolBox/OneBtnModal";
import { useNavigate } from "react-router-dom";


function ChangePassword() {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const navigate = useNavigate();

  //유효성검사
  const validatePwd = (password:string) => {
    return password
      .toLowerCase()
      .match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/);
  };
  
  const [passModal,setPassModal] = useState<boolean>(false);
  const [passModalMsg,setPassModalMsg] = useState<string>('');
  const [passModalSuccess,setPassModalSuccess] = useState<boolean>(false);



  const [passMsg, setPassMsg] = useState<string>("");
  const [currentPassMsg, setcurrentPassMsg] = useState<string>("");
  const [conPassMsg, setConPassMsg] = useState<string>("");

  const [passWord, setPassWord] = useState<string>("");
  const [currentPassWord, setCurrentPassWord] = useState<string>("");
  const [conPassWord, setConPassWord] = useState<boolean>(false);


  const [passInnerColor,setPassInnerColor] = useState<boolean>(false);
  const [currentPassInnerColor,setCurrentPassInnerColor] = useState<boolean>(false);
  const [conPassInnerColor,setConPassInnerColor] = useState<boolean>(false);

  const modalButtonCallback = ()=>{
    if(passModalSuccess){
      setPassModal(false)
      navigate('/')
    }else{
      setPassModal(false)
    }
  }




  const onChangeCurrentPass = (e:React.ChangeEvent<HTMLInputElement>) => {
    const currentPass = e.target.value;
    setCurrentPassWord(currentPass);
    if(!currentPass){
      setcurrentPassMsg("")
    }else{
      if (validatePwd(currentPass)) {
        setcurrentPassMsg("올바른 비밀번호 입니다.");
        setCurrentPassInnerColor(true)
      } else {
        setcurrentPassMsg("영문 + 숫자 + 특수문자를 포함한 8자리 이상");
        setCurrentPassInnerColor(false)
      }

    }
    
  };

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
  


  
const isPassValid = validatePwd(passWord) && validatePwd(currentPassWord) && conPassWord;


const changePassPost = ()=>{
  const NewPass= {
    newPassword:passWord,
    password:currentPassWord
  }
  moim.put('/api/user/update/password',NewPass,{headers:{
    Authorization: `Bearer ${userInfo.accessToken}`
  }}).then((response)=>{
    if(response.data.success==true){
      setPassModal(true)
      setPassModalMsg(response.data.response)
      setPassModalSuccess(true)
      // 성공시 모달안에 들어갈함수
    }else{
      setPassModal(true)
      setPassModalMsg(response.data.error.message)
      setPassModalSuccess(false)
      // 실패시 모달에 들어갈 함수 
    }
  
    // console.log(response)
  }).catch((err)=>{
    // console.log(err)
  })
}



  return (
   <div>
    <BackLogoHeader name="비밀번호 변경" left="15%" fontSize="2rem" top="2.3%"/>

    <div className={styles.mainBox}>
      
      <PassBox innerText="현재 비밀번호" change={onChangeCurrentPass} inner={currentPassInnerColor} helpMsg={currentPassMsg} />
      <PassBox innerText="비밀번호" change={onChangePass} inner={passInnerColor} helpMsg={passMsg} />
      <PassBox innerText="비밀번호확인" change={onChangeConPass} inner={conPassInnerColor} helpMsg={conPassMsg} />
      
      {passModal?<OneBtnModal width="90vw" height="30vh" contentText={passModalMsg} contentFont="1.7rem"  btnTextColor="black" btnText="확인" callback={modalButtonCallback} /> :""}      
      
    </div>

      <div className={styles.btnBox} >
        
        {isPassValid ? <button className={styles.signUpBtn} onClick={changePassPost} >변경하기</button> :
        <button className={styles.signUpBtnDis} disabled>변경하기</button>}
        
      </div>
    
   </div>
  );
};

export default ChangePassword;
