import styles from "./DiffProfile.module.css";
import { Account, UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import {useEffect, useState} from "react"
import {  useLocation, useNavigate } from "react-router-dom";
import InputBtnModal from "../../toolBox/InputBtnModal";
import { moim } from "../../../api/api";
import Nav from "../../Nav";


interface diffUser{
  account:string|null;
  bankCode: number|null;
  bankName: string|null;
  birth:string|null;
  imageNumber:number;
  introduce:string|null;
  mine:boolean;
  name:string;
  nickName:string;
  phoneNumber:number;
}

type DiffPro = UserType &{
  id:number;
  userId:number;
}

interface requestF{
  createdAt: string
  friendId: number
  id: number
  status: number
}

function DiffProfile() {
  const location = useLocation();
  const {state} = useLocation();
  const userId = state.diffuserId
  const [isLoading,setIsLoading] = useState<boolean>(false);
  const [profile,setProfile] = useState<diffUser>()
  const [requestRelation,setRequestRelation] = useState<number>(0);
  const [friendId,setFriendId] = useState<number>();
  const [requestId,setRequestId] = useState<number>();


  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  // 아무사이도 아닌 상태 0 , 내가 친구보내고 안받은상태 1, 내가 친구받은상태 2 , 친구상태 3

  const typeZero = ()=>{
    moim.post(`/api/friend/request/${userId}`,null,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      console.log(response)
    }).catch((err)=>{
      console.log(err)
      console.log(userId)
      console.log(userInfo.accessToken)
    })
  }


  const typeOne = ()=>{
    moim.delete(`/api/friend/request/delete/${userId}`,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((respnse)=>{
      console.log(respnse)
    })

  }


  const typeTwo = ()=>{
    moim.put(`api/friend/request/deny/${1}`,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      console.log(response)
    }).catch((err)=>{
      console.log(err)
    })
  }

  const typeThree = ()=>{

  }




// 아무사이도 아닌 상태 0 , 내가 친구보내고 안받은상태 1, 내가 친구받은상태 2 , 친구상태 3

  

  useEffect(()=>{

    moim.get('api/friend/request/send/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      
      const myQList = response.data.response.list
      console.log(myQList)
      myQList.filter((p:requestF,index:number)=>{
        if(p.friendId == userId){
          console.log(1)
          setRequestRelation(1)
          setRequestId(p.id)
          window.location.reload()
        }
         
      })
    })

    moim.get('api/friend/request/receive/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      const myQList = response.data.response.list
      myQList.find((p)=>{
        if(p.friendId == userId){
          setRequestRelation(2)
        }
         
      })
    })




    moim.get('api/friend/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      const myFList = response.data.response.list
 
      myFList.find((profile:DiffPro)=>{
        if(profile.id == userId){
          setRequestRelation(3)
          setFriendId(profile.id)
        }
      })

    })

    




    moim.get(`/api/user/profile/${userId}`,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      const diffProfile = {
        account:response.data.response.account,
        bankCode: response.data.response.bankCode,
        bankName: response.data.response.bankName,
        birth:response.data.response.birth,
        imageNumber:response.data.response.imageNumber,
        introduce:response.data.response.introduce,
        mine:response.data.response.mine,
        name:response.data.response.name,
        nickName:response.data.response.nickName,
        phoneNumber:response.data.response.phoneNumber
      }
      setProfile(diffProfile)
      // console.log(response)
    }).catch((err)=>{
      // console.log(err)
    })
    .finally(()=>{
      setIsLoading(true)
    })
 

  },[])


  return (
    <div>
      {isLoading? 
      

      <div  className={styles.mainContainer}>
    
      <p style={{fontWeight:"bold",fontSize:"3rem",margin:"0",marginTop:"10%"}}>{profile?.nickName} 의페이지</p>
      <div className={styles.characterBox}>
        <img style={{width:"90%"}}  src={`/src/assets/characterImg/${profile?.imageNumber}.png`} alt="" />
       </div>

       <p style={{fontWeight:"bold",fontSize:"2rem",margin:"0",marginTop:"3%"}}>{profile?.nickName} </p>
        {requestRelation == 3 ? <p style={{fontSize:"1.5rem",fontWeight:"bold",color:"#969696",margin:"0",marginTop:"1%",marginBottom:"3%"}}>{profile?.name}</p> :""}
        {requestRelation == 0 ? <button onClick={typeZero} className={styles.friendBtn} style={{backgroundColor:"#3772FF",color:"white"}}>친구요청</button> : ""}
        {requestRelation == 1 ? <button className={styles.friendBtn} style={{backgroundColor:"#C2C2C2",color:"white"}} >승인대기</button> : ""}
        {requestRelation == 2 ? <button className={styles.friendBtn} style={{backgroundColor:"white",color:"#3772FF"}}>수락하기</button> : ""}
        {requestRelation == 3 ? <button className={styles.friendBtn} style={{backgroundColor:"white",color:"#3772FF"}}>나의친구</button> : ""}

        {requestRelation == 3 ? 
        <div className={styles.accountBox}>
        <img src={`/src/assets/Bank_Logo/${profile?.bankCode}.svg`} alt="" />
        <div style={{display:"flex",flexDirection:"column"}}>
          <p style={{margin:"0",color:"#6C6C6C"}}>{profile?.bankName}</p>
          <p style={{margin:"0"}}>{profile?.account}</p>
        </div>
      </div> : ""}


        <div className={styles.bottomMemo}>
          <p style={{fontSize:"2rem",fontWeight:"bold", margin:"0",}}>소개</p>
          <div style={{backgroundColor:"white",width:"100%",minHeight:"10rem",borderRadius:"0.8rem",display:"flex",justifyContent:"center",alignItems:"center"}}>
           <p>{profile?.introduce}</p>
            
          </div>
          </div>

      </div> 
 
 
 
 : ""}
   
      <Nav/>

   </div>
  );
};

export default DiffProfile;
