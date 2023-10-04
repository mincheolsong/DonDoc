import styles from "./Home.module.css";
import Nav from "../../Nav";
import Header from "../Header";
import  {useEffect,useState}  from "react";
import { useNavigate } from "react-router-dom";
import { moim } from "../../../api/api";
import { UserType,Account } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";

function Home() {
  Number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const goSendMoney = (account:Account) =>{
    navigate(`/sendmoneyfirst/${account.accountId}`,{state:{account:account}})
  }
  const goAccountInfo = (account:Account) =>{
    navigate(`/accountinfo/${account.accountNumber}`,{state:{account:account}})
  }
  const navigate = useNavigate();
  const goMakeAccount = () =>{
    navigate("makeAccount")
  }
  const [allAccount,setAllAccount] =useState<Account[]>([])


  useEffect(()=>{
    moim.get("/api/account/account/list",{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      const formattedAccountList = response.data.response.accountList.map((account:Account) => ({
        ...account,
        balance: account.balance.toLocaleString(), // 잔액을 포맷팅하여 문자열로 변환
      }));
      setAllAccount(formattedAccountList)
  
    })
    .catch((err)=>{
      // console.log(err)
    })
    // console.log(userInfo)
  },[])

  return (
    <div className={styles.container}>
      {/* 헤더, 유저박스 */}
      <Header/>
      <div style={{display:"flex" , flexDirection:"column",  alignItems:"center", marginTop:"1.5rem"}}>
      <UserBox userId={userInfo.phoneNumber} userCharacter={`src/assets/characterImg/${userInfo.imageNumber}.png`} username={userInfo.name} rightBtn="계좌개설하기" rightBtnClick={goMakeAccount} />

      {/* 헤더, 유저박스 */}


    {/* 나의계좌 */}
    <div style={{display:"flex",flexDirection:"row",justifyContent:"space-between",alignItems:"end",width:"98%",marginTop:"1rem"}}>
    <p style={{fontSize:"2.2rem",fontWeight:"bold", marginBottom:"1rem"}}>나의 계좌</p>
    <button className={styles.accountBtn} onClick={()=>{navigate("/callaccount")}}>계좌불러오기</button>
    </div>
     {/* 나의계좌 */}
     
     
     
     
    {allAccount.map((account,index)=>(
      <div onClick={()=>{
        goAccountInfo(account)
      }} className={styles.midContainer}>
      <div style={{display:"flex",flexDirection:"row"}}>
      <img style={{marginLeft:"7%",width:"25%",height:"100%"}} src={`/src/assets/Bank_Logo/${account.bankCode}.svg`} alt="" />
      <div style={{width:"20rem",fontWeight:"bold",marginLeft:"3rem"}}>
      <p style={{margin:"0",fontSize:"1.2rem",color:"#6C6C6C"}}>{account.accountName}</p>
      <br /> 
      <p style={{margin:"0",fontSize:"1.6rem"}}>{account.balance}원</p>
    </div>
    </div>
    
        <button onClick={(e)=>{
          e.stopPropagation()
          goSendMoney(account)}} className={styles.sendMoneyBtn}>송금</button>
        
    </div>
    ))}
    
  
          <button onClick={()=>{
            const diffuserId = 1
            navigate(`/diffprofile/${diffuserId}`, {state:{diffuserId:diffuserId}})
          }}>다른프로필</button>


      <Nav/>
      </div>
    </div>
  );
}

export default Home;


export function UserBox(props){
  const navigate = useNavigate();
  return(
    <div className={styles.topContainer}>
      <div style={{display:"flex",width:"60%"}}>
      <img src={props.userCharacter} style={{width:"35%"}} />
    <div style={{marginLeft:"1rem",textAlign:"center"}}>
      <p style={{fontSize:"1.2rem",fontWeight:"bold"}}>{props.username} 의 DonDoc</p>
      <button className={styles.myProfileBtn} onClick={()=>{
        navigate(`/mypage/${props.userId}`)
      }}> 나의프로필가기</button>
    </div>
      </div>
    
    <div>
      <button onClick={()=>{
        {props.rightBtnClick()}
      }} className={styles.myProfileBtn} style={{height:"5rem",fontSize:"1.2rem"}} > {props.rightBtn}</button>
    </div>
  </div>

  )

}

