import styles from "./Mypage.module.css";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import Nav from "../../Nav";
import {useEffect, useState} from "react"
interface Props {

}

function Mypage(props: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  
  useEffect(()=>{
    console.log(userInfo)
  },[])

  return (
    <div className={styles.mainContainer}>
      <img className={styles.settingIcon} src={"/src/assets/image/setting.svg"} alt="" />
      <p style={{fontWeight:"bold",fontSize:"3rem",margin:"0",marginTop:"10%"}}>마이페이지</p>
      {userInfo.imageNumber==0 ? <img onClick={()=>{
          console.log('12')
        }} style={{marginTop:"5%",marginBottom:"2%",width:"40%"}} src={"/src/assets/image/NoUserIcon.svg"} alt="" />
       :
       <div onClick={()=>{
        console.log('12')
      }} className={styles.characterBox}>
        <img style={{width:"90%"}}  src={`/src/assets/characterImg/${userInfo.imageNumber}.png`} alt="" />
       </div>
        }
        <div onClick={()=>{
          console.log('15')
        }} style={{display:"flex",justifyContent:"center",alignItems:"baseline",width:"40%",marginTop:"2%"}}>
        <p style={{fontWeight:"bold",fontSize:"2rem",margin:"0",marginLeft:"12%"}}>{userInfo.nickname} </p><img style={{marginLeft:"5%",width:"1.2rem"}} src={'/src/assets/image/pencil.svg'} alt="" />
        </div>
        <p style={{fontSize:"1.4rem",fontWeight:"bold",color:"#969696",margin:"0",marginTop:"1%",marginBottom:"3%"}}>{userInfo.name}</p>
        <img style={{width:"30%"}} src="/src/assets/image/friendList.svg" alt="" />

        <div style={{width:"80%",display:"flex",justifyContent:"space-between", alignItems:"center",marginTop:"3%" }}>
          <p style={{fontWeight:"bold",fontSize:"2rem"}}>대표계좌</p><img style={{width:"32%"}} src="/src/assets/image/moimBtn.svg" alt="" />
        </div>

        {userInfo.mainAccount ? <div className={styles.accountBox}>
        <img src={`/src/assets/Bank_Logo/${Account.bankCode}.svg`} alt="" />
        <div style={{display:"flex",flexDirection:"column"}}>
          <p style={{margin:"0",color:"#6C6C6C"}}>{Account.accountName}</p>
          <p style={{margin:"0"}}>{Account.accountNumber}</p>
        </div>
      </div>
         : ''}

          
         <div className={styles.bottomMemo}>
          <p style={{fontSize:"2rem",fontWeight:"bold", margin:"0",}}>소개</p>
          <div style={{backgroundColor:"white",width:"100%",minHeight:"10rem",borderRadius:"0.8rem",display:"flex",justifyContent:"center",alignItems:"center"}}>
            {userInfo.introduce ? <p>{userInfo.introduce}</p> : <p style={{fontSize:"1.5rem",color:"#9F9F9F"}}>소개를 입력해주세요</p> }
            
          </div>
         </div>


       <Nav/>
    </div>
  );
};

export default Mypage;
