import styles from "./Mypage.module.css";
import { Account, UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import Nav from "../../Nav";
import {useEffect, useState} from "react"
import { useNavigate } from "react-router-dom";
import InputBtnModal from "../../toolBox/InputBtnModal";
import { moim } from "../../../api/api";
import { changeIntroduce,changeNickName } from "../../../store/slice/userSlice";
import { useDispatch } from "react-redux";

import { useLocation } from "react-router-dom";

function Mypage() {

  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const [mainAccount,setMainAccount] = useState<Account>()
  const [mainAccountCheck,setMainAccountCheck] = useState<boolean>(false);

  
// 모달 
  const [nickNameModal,setNickNameModal] = useState<boolean>(false);
  const [introduceModal,setIntroduceModal] = useState<boolean>(false);

//  

//모달이벤트 닉네임
  const nickNameChangeR = (nickname:string)=>{
    moim.put('/api/user/update/nickname',{nickName:nickname},{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      dispatch(changeNickName({nickname:nickname}))
      // console.log(response)
    }).catch((err)=>{
      // console.log(err)
    })
    setNickNameModal(false)
  }

  const nickNameChangeL = (nickname:string)=>{
    // console.log(nickname)
    setNickNameModal(false)
  }

// 모달이벤트 소개

const introduceChangeR = (intro:string)=>{
  moim.put('/api/user/profile/introduce',{introduce:intro},{headers:{
    Authorization: `Bearer ${userInfo.accessToken}`
  }}).then((response)=>{
    dispatch(changeIntroduce({introduce:intro}))
    // console.log(response)
  }).catch((err)=>{
    // console.log(err)
  })
  setIntroduceModal(false)
}

const introduceChangeL = (intro:string)=>{
  // console.log(intro)
  setIntroduceModal(false)
}





useEffect(()=>{ 
  moim.get(`/api/account/account/detail/${userInfo.mainAccount}`,{headers:{
    Authorization: `Bearer ${userInfo.accessToken}`}})
    .then((response)=>{
      setMainAccount(response.data.response.accountDetail)
      if(response.data.response.accountDetail){
        setMainAccountCheck(true)
      }
    }).catch((err)=>{
      console.log(err)
    })
  },[])



  useEffect(()=>{
    // console.log(userInfo)
    // console.log(location.pathname)
  },[])

  return (
    <div className={styles.mainContainer}>
      {nickNameModal ? <InputBtnModal callbackRight={nickNameChangeR} callbackLeft={nickNameChangeL} leftBtnText="닫기" leftBtnColor="white" rightBtnColor="#3772FF" rightBtnText="변경하기" rightBtnTextColor="white" contentFont="1.5rem" contentText={userInfo.nickname} width="90vw" height="35vh"/> : ''}
      <img onClick={()=>{
        navigate('/setting')
      }} className={styles.settingIcon} src={"/src/assets/image/setting.svg"} alt=""/>
      <p style={{fontWeight:"bold",fontSize:"3rem",margin:"0",marginTop:"10%",fontFamily:"NT"}}>마이페이지</p>

      {/* 캐릭터 */}
      {userInfo.imageNumber==0 ? <img onClick={()=>{
          navigate("/changecharacter")
        }} style={{marginTop:"5%",marginBottom:"2%",width:"40%"}} src={"/src/assets/image/NoUserIcon.svg"} alt="" />
       :
       <div onClick={()=>{
        navigate("/changecharacter")
      }} className={styles.characterBox}>
        <img style={{width:"90%"}}  src={`/src/assets/characterImg/${userInfo.imageNumber}.png`} alt="" />
       </div>
        }
        {/* 캐릭터 */}
        
        {introduceModal ? <InputBtnModal callbackRight={introduceChangeR} callbackLeft={introduceChangeL} leftBtnText="닫기" leftBtnColor="white" rightBtnColor="#3772FF" rightBtnText="변경하기" rightBtnTextColor="white" contentFont="1.8rem" contentText={userInfo.introduce} width="90vw" height="35vh"/> : ''}

        {/* 이름 */}
        <div onClick={()=>{
          setNickNameModal(true)
        }} style={{display:"flex",justifyContent:"center",alignItems:"baseline",width:"40%",marginTop:"2%"}}>
        <p style={{fontWeight:"bold",fontSize:"2rem",margin:"0",marginLeft:"12%",fontFamily:"NT"}}>{userInfo.nickname} </p><img style={{marginLeft:"5%",width:"1.2rem"}} src={'/src/assets/image/pencil.svg'} alt="" />
        </div>
        <p style={{fontSize:"1.4rem",fontWeight:"bold",color:"#969696",margin:"0",marginTop:"1%",marginBottom:"3%",fontFamily:"NT"}}>{userInfo.name}</p>
         {/* 이름 */}


       {/* 친구목록 */}
        <img onClick={()=>{navigate('/friendlist')}} style={{width:"30%"}} src="/src/assets/image/friendList.svg" alt="" />
        {/* 친구목록 */}


{/* 대표계좌 */}
        <div style={{width:"80%",display:"flex",justifyContent:"space-between", alignItems:"center",marginTop:"3%" }}>
          <p style={{fontWeight:"bold",fontSize:"2rem",fontFamily:"BD"}}>대표계좌</p><img onClick={()=>{
            navigate('/accountlist')
          }} style={{width:"32%"}} src="/src/assets/image/moimBtn.svg" alt="" />
        </div>




        {mainAccountCheck? <div className={styles.accountBox}>
        <img style={{marginLeft:"4%"}} src={`/src/assets/Bank_Logo/${mainAccount?.bankCode}.svg`} alt="" />
        <div style={{display:"flex",flexDirection:"column",fontFamily:"NT",marginLeft:"5%"}}>
          <p style={{margin:"0",color:"#6C6C6C",fontSize:"1.3rem"}}>{mainAccount?.accountName}</p>
          <p style={{margin:"0",fontSize:"1.4rem"}}>{mainAccount?.accountNumber}</p>
        </div>
      </div>
         : ''} 
{/* 대표계좌
          

          {/* 소개 */}
         <div className={styles.bottomMemo}>
          <p style={{fontSize:"2rem",fontWeight:"bold", margin:"0",marginBottom:"3%"}}>소개</p>
          <div onClick={()=>{
            setIntroduceModal(!introduceModal)
          }} style={{backgroundColor:"white",width:"100%",minHeight:"10rem",borderRadius:"0.8rem",display:"flex",justifyContent:"center",alignItems:"center"}}>
            {userInfo.introduce ? <p style={{fontFamily:"BD",fontWeight:"bold",fontSize:"1.4rem"}}>{userInfo.introduce}</p> : <p style={{fontSize:"1.5rem",color:"#9F9F9F",fontFamily:"BD",fontWeight:"bold"}}>소개를 입력해 주세요.</p> }
            
          </div>

          {/* 소개 */}
         </div>


       <Nav/>
    </div>
  );
};

export default Mypage;
