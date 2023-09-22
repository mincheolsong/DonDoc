import styles from "./DetailThird.module.css";

import TowBtnModal from "../../toolBox/TowBtnModal";

import {useState} from 'react'


function DetailThird() {

  const [modalOpen,setModalOpen] = useState<boolean>(true)
  const setModalFunc = ()=>{
    return(
      console.log('12')
      )
  } 

  return (
    
    <div>
      <button onClick={()=>{
        setModalOpen(!modalOpen)
      }}>껏다키기</button>


      {modalOpen ? <TowBtnModal width="85%" height="25vh" titleText="ㅋㅋㅋ" title={true} titleFont="2rem" contentFont="1rem" contentText="하하하하"
      leftBtnColor = "#FF001F" leftBtnTextColor="white" rightBtnColor = "#3772FF" rightBtnTextColor ="white" 
      leftBtnText = "취소하기" rightBtnText ="수락하기" callbackLeft={setModalOpen} callbackRight={setModalFunc}
      />  : '' }

    </div>
  );
}

export default DetailThird;




