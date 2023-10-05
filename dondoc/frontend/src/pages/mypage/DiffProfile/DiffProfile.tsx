import styles from "./DiffProfile.module.css";
import { Account, UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import {useEffect, useState} from "react"
import {  useLocation, useNavigate, useParams } from "react-router-dom";
import { moim } from "../../../api/api";
import Nav from "../../Nav";
import { TowBtnModal } from "../../toolBox/TowBtnModal/TowBtnModal";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
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
  const {userId} = useParams()
  const userIdN = userId? parseInt(userId,10) : undefined;
  const [isLoading,setIsLoading] = useState<boolean>(false);
  const [profile,setProfile] = useState<diffUser>()
  const [requestRelation,setRequestRelation] = useState<number>(0);
  const [friendId,setFriendId] = useState<number>();
  const [requestId,setRequestId] = useState<number>();
  const [resiveId,setResiveId] = useState<number>();
  const [deleteFriendModal,setDeleteFriendModal] = useState<boolean>(false);
  const [accessModal,setAccessModal] = useState<boolean>(false);

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  // 아무사이도 아닌 상태 0 , 내가 친구보내고 안받은상태 1, 내가 친구받은상태 2 , 친구상태 3

  const typeZero = ()=>{
    moim.post(`/api/friend/request/${userId}`,null,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      console.log(response)

      moim.get('api/friend/request/send/list',{headers:{
        Authorization: `Bearer ${userInfo.accessToken}`
      }}).then((response)=>{
        const myQList = response.data.response.list
        myQList.filter((p)=>{
          if(p.userId == userIdN){
            setRequestRelation(1)
            setRequestId(p.id)
          }
           
        })
      }).catch((err)=>{
        // console.log(err)
      })
    }).catch((err)=>{
      console.log(err)
 
    })
  }


  const typeOne = ()=>{
    moim.delete(`/api/friend/request/delete/${requestId}`,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      console.log(response)
      setRequestRelation(0)
    }).catch((err)=>{
      console.log(err)
    })

  }

  const closeAccessModal = ()=>{
    setAccessModal(false)
  }
  
  const typeTwo = ()=>{
    moim.put(`api/friend/request/access/${resiveId}`,null,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
     
      moim.get('api/friend/list',{headers:{
        Authorization: `Bearer ${userInfo.accessToken}`
      }})
      .then((response)=>{
        const myFList = response.data.response.list
        // console.log(myFList)
        myFList.find((profile:DiffPro)=>{
          if(profile.userId == userIdN){
            setRequestRelation(3)
            setFriendId(profile.id)
          }
        })
  
      }).catch((err)=>{
        // console.log(err)
      })

      setAccessModal(false)
    }).catch((err)=>{
      console.log(err)
    })
  }

  const closeDeleteFriendModal =()=>{
    setDeleteFriendModal(false)
  }
  
  const typeThree = ()=>{
    moim.delete(`/api/friend/delete/${friendId}`,{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{

      setRequestRelation(0)
      setDeleteFriendModal(false)

    })
    .catch((err)=>{
      console.log(err)
    })
  }




// 아무사이도 아닌 상태 0 , 내가 친구보내고 안받은상태 1, 내가 친구받은상태 2 , 친구상태 3

  

  useEffect(()=>{
// 보낸 요청목록조회
    moim.get('/api/friend/request/send/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
   
      const myQList = response.data.response.list
      myQList.filter((p)=>{
        if(p.userId == userIdN){
          setRequestRelation(1)
          setRequestId(p.id)
        }
         
      })
    }).catch((err)=>{
      // console.log(err)
    })


    moim.get('api/friend/request/receive/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
   
      const myQList = response.data.response.list
      myQList.find((p)=>{
        if(p.id == userIdN){
          setRequestRelation(2)
          setResiveId(p.id)
        }
         
      })
    }).catch((err)=>{
      // console.log(err)
    })




    moim.get('/api/friend/list',{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      const myFList = response.data.response.list
      myFList.find((profile:DiffPro)=>{
        if(profile.userId == userIdN){
          setRequestRelation(3)
          setFriendId(profile.id)
        
        }
      })

    }).catch((err)=>{
      // console.log(err)
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
      <BackLogoHeader/>
      {isLoading? 
      

      <div  className={styles.mainContainer}>
    
      <p style={{fontWeight:"bold",fontSize:"3rem",margin:"0",marginTop:"10%",fontFamily:"NT"}}>{profile?.nickName} 의페이지</p>
      <div className={styles.characterBox}>
        <img style={{width:"90%"}}  src={`/src/assets/characterImg/${profile?.imageNumber}.png`} alt="" />
       </div>
       {accessModal ? <TowBtnModal width="90vw" height="30vh" contentText="친구요청을 수락하시겠습니까?" contentFont="1.5rem" rightBtnText="수락하기" leftBtnText="취소" rightBtnColor="#3772FF" rightBtnTextColor="white" leftBtnColor="white" leftBtnTextColor="black" callbackLeft={closeAccessModal} callbackRight={typeTwo}  /> : ''}
       {deleteFriendModal ? <TowBtnModal width="90vw" height="30vh" contentText="친구 삭제를 하시겠습니까?" contentFont="1.5rem" rightBtnText="취소" leftBtnText="친구삭제" rightBtnColor="white" rightBtnTextColor="black" leftBtnColor="#FF001F" leftBtnTextColor="white" callbackLeft={typeThree} callbackRight={closeDeleteFriendModal}  /> : ""}

       <p style={{fontWeight:"bold",fontSize:"2rem",margin:"0",marginTop:"3%"}}>{profile?.nickName} </p>
        {requestRelation == 3 ? <p style={{fontSize:"1.5rem",fontWeight:"bold",color:"#969696",margin:"0",marginTop:"1%",marginBottom:"1%"}}>{profile?.name}</p> :""}
        {requestRelation == 0 ? <button onClick={typeZero} className={styles.friendBtn} style={{backgroundColor:"#3772FF",color:"white"}}>친구요청</button> : ""}
        {requestRelation == 1 ? <button onClick={typeOne} className={styles.friendBtn} style={{backgroundColor:"#C2C2C2",color:"white"}} >승인대기</button> : ""}
        {requestRelation == 2 ? <button onClick={()=>{setAccessModal(!accessModal)}} className={styles.friendBtn} style={{backgroundColor:"white",color:"#3772FF",fontWeight:"bold"}}>수락하기</button> : ""}
        {requestRelation == 3 ? <button onClick={()=>{setDeleteFriendModal(!deleteFriendModal)}}className={styles.friendBtn} style={{backgroundColor:"white",color:"#3772FF",fontWeight:"bold"}}>나의친구</button> : ""}

        {requestRelation == 3 ? 
        <div className={styles.accountBox}>
        <img style={{marginLeft:"4%"}} src={`/src/assets/Bank_Logo/${profile?.bankCode}.svg`} alt="" />
        <div style={{display:"flex",flexDirection:"column",fontFamily:"NT",marginLeft:"5%"}}>
          <p style={{margin:"0",color:"#6C6C6C",fontSize:"1.3rem"}}>{profile?.bankName}</p>
          <p style={{margin:"0",fontSize:"1.4rem"}}>{profile?.account}</p>
        </div>
      </div> : ""}


        <div className={styles.bottomMemo}>
          <p style={{fontSize:"2rem",fontWeight:"bold", margin:"0",marginBottom:"3%"}}>소개</p>
          <div style={{backgroundColor:"white",width:"100%",minHeight:"10rem",borderRadius:"0.8rem",display:"flex",justifyContent:"center",alignItems:"center"}}>
           <p style={{fontFamily:"BD",fontWeight:"bold",fontSize:"1.4rem"}}>{profile?.introduce}</p>
            
          </div>
          </div>

      </div> 
 
 
 
 : ""}
   
      <Nav/>

   </div>
  );
};

export default DiffProfile;
