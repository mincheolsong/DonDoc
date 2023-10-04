import styles from "./Mission.module.css";
import { useState, useEffect } from 'react'
import Header from "../../webmain/Header/Header";
import Nav from "../../Nav/Nav";
import { useSelector } from "react-redux/es/hooks/useSelector";
import { UserType } from "../../../store/slice/userSlice";
import axios from "axios";
import { BASE_URL } from "../../../constants";


type Mission = {
  id: number,
  moimName: string,
  title: string,
  content: string,
  amount: number,
  endDate: string
}



function Mission() {

  const [MissionList, setMissionList] = useState<Mission[]>([])

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const token = userInfo.accessToken

  useEffect(() => {
    axios.get(`${BASE_URL}/api/moim/my_mission`, {
      headers:{
        Authorization: `Bearer ${token}`}
    })
    .then((res) => {
      console.log(res)
      setMissionList(res.data.response)
    })
    .catch((err) => {
      console.log(err)
    })
  },[])

  return (
    <>
    <Header />
    <div>
    <img style={{marginTop:"5%",marginBottom:"2%",marginLeft:"30%",width:"40%"}} src={`/src/assets/characterImg/${userInfo.imageNumber}.png`} alt="" />
    <div style={{marginTop:"5%",marginBottom:"2%",marginLeft:"33%",width:"40%", fontSize:"2.8rem"}}>나의 미션</div>
    </div>
    { MissionList.length ?
    <div>1</div> :
    <div>등록된 미션이 없습니다.</div>}
      <Nav />
    </>
  );
};

export default Mission;
