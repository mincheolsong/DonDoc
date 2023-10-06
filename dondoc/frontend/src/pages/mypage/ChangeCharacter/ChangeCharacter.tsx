import styles from "./ChangeCharacter.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import {useState} from "react"
import { UserType } from "../../../store/slice/userSlice";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { moim } from "../../../api/api";
import { useDispatch } from "react-redux";
import { changeImage } from "../../../store/slice/userSlice";
import { useNavigate } from "react-router-dom";

function ChangeCharacter() {

  const number:number[] = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22]

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [characterSelected,setCharacterSelected] = useState<boolean>(false)
  const [selectedNum,setSelectedNum] = useState<number>(0)
  const changeCharacter = ()=>{
    const sendInfo = {
      imageNumber:selectedNum,
      nickName:userInfo.nickname
    }
    moim.put(`/api/user/update/image`,{imageNumber:selectedNum},{headers:{
      Authorization: `Bearer ${userInfo.accessToken}`
    }}).then((response)=>{
      // console.log(response)
    }).catch((err)=>{
      // console.log(err)
    })

    dispatch(changeImage({imageNumber:selectedNum}))
    navigate(`/mypage/${userInfo.phoneNumber}`)
  }


  return (
    <div>
      <BackLogoHeader name="캐릭터 변경" fontSize="2rem" left="15%" top="2.6%"/>
      <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"center"}}>
      {characterSelected ? <img style={{height:"40vh"}} src={`/src/assets/characterImg/${selectedNum}.png`} alt="" /> : <img style={{height:"40vh"}} src={`/src/assets/characterImg/${userInfo.imageNumber}.png`} alt="" />}
      
      <div className={styles.midContainer}>
        {number.map((num,index)=>(
          <img onClick={()=>{
            setCharacterSelected(true)
            setSelectedNum(num)
          }} className={styles.characterIcon} src={`/src/assets/characterImg/${num}.png`} alt="" />
        ))}
      </div>
      <button onClick={changeCharacter} className={styles.selectBtn}>등록하기</button>
      </div>
    </div>
  );
};

export default ChangeCharacter;
