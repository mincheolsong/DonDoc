import styles from "./FriendList.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import {BiSearch} from "react-icons/bi"
import { moim } from "../../../api/api";
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import {useEffect, useState} from "react"
import { useNavigate } from "react-router-dom";
import { TowBtnModal } from "../../toolBox/TowBtnModal/TowBtnModal";

export interface friend{
  accountNumber: string | null;
  bankCode: number| null;
  bankName: string | null
  id: number
  imageNumber: string
  name: string
  phoneNumber: string;
  userId:number;
}
function FriendList(props) {


  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const navigate = useNavigate();
  const [searchBox,setSearchBox] = useState<string>('')
  const [friendList,setFriendList] = useState<friend[]>([])
  const [searchFriend,setSearchFriend] = useState<friend[]>([]) 
  const [deleteFBox,setDeleteFBox] =useState<boolean>(false);
  const [deleteId, setDeleteId] = useState<number>();

  const searchF = (e)=>{
  const currentT = e.target.value
  setSearchBox(currentT)
  const searchF = friendList.filter((item)=>{
      return item.phoneNumber.includes(currentT) || item.name.includes(currentT)
  })
  setSearchFriend(searchF)
}

const deleteF = ( params:number, e:React.MouseEvent<HTMLButtonElement> )=>{
  e.stopPropagation()
  setDeleteFBox(true)
  setDeleteId(params)
}

  useEffect(()=>{

    moim.get("/api/friend/list",{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      console.log(response)
      setFriendList(response.data.response.list)
    }).catch((err)=>{
      // console.log(err)
    })
  },[])

  const deleteFriendL = ()=>{
    moim.delete(`/api/friend/delete/${deleteId}`,
    {headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      setDeleteFBox(false)
     moim.get("/api/friend/list",{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      // console.log(response)
      setFriendList(response.data.response.list)
    }).catch((err)=>{
      // console.log(err)
    })
    }).catch((err)=>{
      // console.log(err)
    })
  }
  
  const deleteFriendR = ()=>{
    setDeleteFBox(false)
  }


  return (
   <div style={{height:"100vh"}}>
      {deleteFBox ?  <TowBtnModal callbackRight={deleteFriendR} callbackLeft={deleteFriendL} leftBtnText="친구 삭제" leftBtnColor="#FF001F" leftBtnTextColor="white" rightBtnColor="white"  rightBtnText="취소" rightBtnTextColor="black" contentFont="1.5rem" contentText="친구 삭제를 하시겠습니까?" width="90vw" height="35vh" /> : ""}
    <BackLogoHeader name="친구목록" left="13%" fontSize="1.5rem" top="3%"/>
    <div className={styles.mainBox}>
      <div style={{marginBottom:"20%"}}>
      <input onChange={searchF} className={styles.searchBox} placeholder="이름, 전화번호" type="text" name="" id="" />
      <BiSearch className={styles.searchIcon}/>
      </div>
    
      <div style={{position:"fixed",bottom:"0",overflow:"scroll", width:"100%",height:"70vh"}}>
     
        {searchBox ? 
        <div>
        {searchFriend.map((friend,index)=>(
          <div key={index}
          onClick={()=>{
            navigate(`/diffprofile/${friend.userId}`)
          }} 
          className={styles.friendBox}>
          <img style={{height:"100%"}} src={`/src/assets/characterImg/${friend.imageNumber}.png`} alt="" />
          <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start",marginLeft:"5%"}}>
            <p style={{fontSize:"1rem",fontWeight:"bold",margin:0,marginBottom:"10%"}}>{friend.name}</p>
            <p style={{fontSize:"1rem",fontWeight:"bold",color:"#848484",margin:0,}}>{friend.phoneNumber}</p>
          </div>
          <button onClick={(e)=>{
            deleteF(friend.id,e )
          }} className={styles.deleteBox}>삭제</button>
        </div>
        ))}

        </div>
        : 
        <div>
        {friendList.map((friend,index)=>(
          <div key={index} onClick={()=>{
            navigate(`/diffprofile/${friend.userId}`)
          }} className={styles.friendBox}>
          <img style={{height:"100%"}} src={`/src/assets/characterImg/${friend.imageNumber}.png`} alt="" />
          <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start",marginLeft:"5%"}}>
            <p style={{fontSize:"1rem",fontWeight:"bold",margin:0,marginBottom:"10%"}}>{friend.name}</p>
            <p style={{fontSize:"1rem",fontWeight:"bold",color:"#848484",margin:0,}}>{friend.phoneNumber}</p>
          </div>
          <button onClick={(e)=>{
            deleteF(friend.id,e)
          }} className={styles.deleteBox}>삭제</button>
        </div>
        ))}
        </div>}
        

        
        
      </div>
    </div>
   </div>
  );
};

export default FriendList;
