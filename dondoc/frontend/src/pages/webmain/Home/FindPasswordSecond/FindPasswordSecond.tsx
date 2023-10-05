import styles from "./FindPasswordSecond.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import BackLogoHeader from "../../../toolBox/BackLogoHeader";
import {useState} from "react"
import { moim } from "../../../../api/api";
import { useDispatch } from "react-redux";
import { loginUser } from "../../../../store/slice/userSlice";
import OneBtnModal from "../../../toolBox/OneBtnModal";

interface PassBox{
  innerText:string;
  change(e:React.ChangeEvent<HTMLInputElement>):void;
  inner:boolean;
  helpMsg:string;
}

function FindPasswordSecond() {

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const {state} = useLocation();
  const phone = state.phone
  const [errText,setErrText] = useState<string>('')
  const [errModal,setErrModal] = useState<boolean>(false)

  //유효성검사
  const validatePwd = (password:string) => {
    return password
      .toLowerCase()
      .match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/);
  };
  

  //

  // 스테이트에 저장된는곳 
  const [passWord, setPassWord] = useState<string>("");

  const [conPassWord, setConPassWord] = useState<boolean>(false);

//
// helpMag 랑 글씨 색깔
const [passMsg, setPassMsg] = useState<string>("");
const [conPassMsg, setConPassMsg] = useState<string>("");



const [passInnerColor,setPassInnerColor] = useState<boolean>(false);
const [conPassInnerColor,setConPassInnerColor] = useState<boolean>(false);

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



//



const isPassValid = validatePwd(passWord);
const isConPassValid = conPassWord;
const isAllVaild = isPassValid && isConPassValid 


const userSetting = {
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
        isUserFirst: false,
        accessToken:response.data.response.accessToken,
        nickname:response.data.response.nickname,
        name:response.data.response.name,
        introduce:response.data.response.introduce,
        mainAccount:response.data.response.mainAccount,
        imageNumber:response.data.response.imageNumber,
      }
      // console.log(response.data.response)
      dispatch(loginUser(userUpdate))

    }else{
      setErrText(response.data.error.message)
    }
    
  })
  .catch(()=>{
  })
}


const PassUserSetting = {
  certification:true,
  password:passWord,
  phoneNumber : phone,
}

const SignUpPost = ()=>{
  moim.put("/api/user/find_password",PassUserSetting)
  .then((response)=>{
    if(response.data.success == true){
      // console.log(response)
    SignInPost();
    navigate('/')
    }else{
      setErrText(response.data.error.message)
      setErrModal(!errModal)
    }
  })
  .catch(()=>{
  })
}


// interface OneBtnModal{
//   width : string;
//   height: string;
//   titleText: string;
//   title: boolean;
//   titleFont:string;
//   contentFont: string;
//   contentText: string;
//   btncolor : string;
//   btnTextColor:string;
//   btnText:string;
//   callback:void;
// }

const closeModal = ()=>{
  setErrModal(!errModal)
}


  return (
    <div>
    
    <BackLogoHeader name=" " left="0" fontSize=" " top="0"/>
      <div className={styles.mainContainer}>
      {errModal ? <OneBtnModal width="80vw" height="50vh" titleText="" title="false" contentText={errText}  contentFont="1.5rem" btncolor="white" btnTextColor="black" btnText="닫기" callback={closeModal}  /> : ""}
        <img className={styles.Logo} src={"/src/assets/image/dondocLogo.png"} />
        <p style={{fontSize:"1.5rem",fontWeight:"bold", marginBottom:"0.5rem",marginTop:"0.3rem",fontFamily:"BD"}}>비밀번호변경</p>
      </div>
  
        <div className={styles.mainContainerBottom}>
  
          <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
          <PassBox innerText="새 비밀번호" change={onChangePass} inner={passInnerColor} helpMsg={passMsg} />
          <PassBox innerText="비밀번호확인" change={onChangeConPass} inner={conPassInnerColor} helpMsg={conPassMsg} />
          </div>
          
            
        </div>
      <div className={styles.btnBox} >
        
        {isAllVaild ? <button className={styles.signUpBtn} onClick={SignUpPost} >비밀번호변경</button> :
        <button className={styles.signUpBtnDis} disabled>비밀번호변경</button>}
        
      </div>
    </div>    
  );
};


export default FindPasswordSecond;
function PassBox(props:PassBox){
  return(
    <div>
      <input type="password" placeholder={props.innerText} className={styles.IdBox} onChange={props.change}/>
      <p style={{margin:"0" , color : props.inner ? "green" : "#FF001F ", fontWeight:"bold",fontFamily:"NT",fontSize:"1.2rem"}}>{props.helpMsg}</p>
    </div>
    
  )
}