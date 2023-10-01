import React, {useState, useEffect} from "react";
import styles from "./InviteModal.module.css";
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";
import MemberUnit from "./MemberUnit/MemberUnit";

interface Props {
  setModalOpen(id: boolean) : void
}

type friendList = { friend: inviteUnit,
  id:number,
  friendId:number,
  createdAt:string}
type inviteUnit = {
  id:number,
  friendId:number,
  createdAt:string
}
const initialSearchResult: searchUnit = {
  userId: 0,
  phoneNumber: "",
  imageNumber: 0,
  bankName: "",
  bankCode: 0,
  accountNumber: "",
  msg: "",
  nickName: ""
};
type searchUnit = {
  userId: number,
  phoneNumber: string,
  imageNumber: number,
  bankName: string,
  bankCode: number,
  accountNumber: string,
  msg: string,
  nickName: string
}
type newInviteUnit = {
  userId: number
}
type newInviteList = {
  userId: number
}


function InviteModal({setModalOpen}: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken
  const BASE_URL = 'http://j9d108.p.ssafy.io:9999'

  const [searchInput, setSearchInput] = useState<string>('')
  const [searchResult, setSearchResult] = useState<searchUnit>(initialSearchResult);
  const [friendList, setFriendList] = useState<friendList[]>([])
  const [inviteList, setInviteList] = useState<newInviteList[]>([])

  const ModalClose = () => {
    setModalOpen(false)
  }

  const ChangeSearchInput = (e:React.ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value)
    // console.log(searchInput)
  }
  
  const AppendInviteList = (friend: inviteUnit) => {
    // 이미 존재하는지 확인
    const isAlreadyAdded = inviteList.some((item) => item.userId === friend.friendId);

    if (!isAlreadyAdded) {
      const newInviteUnit: newInviteUnit = {
        userId: friend.friendId,
      };

      const newInviteList = [...inviteList, newInviteUnit];
      setInviteList(newInviteList);
    }
  };

  // const AppendSearchUnit = (unit: searchUnit) => {
  //   const isAlreadyAdded = inviteList.some((item) => item.userId === unit.userId);
  //   const isSearchResult = searchResult.phoneNumber

  //   if (!isAlreadyAdded && isSearchResult) {
  //     const newInviteUnit: newInviteUnit = {
  //       "userId": unit.userId,
  //     };

  //     const newInviteList = [...inviteList, newInviteUnit];
  //     setInviteList(newInviteList);
  //   }
  // };
  const AppendSearchUnit = (unit: searchUnit) => {
    if (searchResult && searchResult.accountNumber) {
      const isAlreadyAdded = inviteList.some((item) => item.userId === unit.userId);
  
      if (!isAlreadyAdded) {
        const newInviteUnit: newInviteUnit = {
          userId: unit.userId,
        };
  
        const newInviteList = [...inviteList, newInviteUnit];
        setInviteList(newInviteList);
      }
    }
  };
  

  const DeleteUnit = (inviteUnit: object) => {
    const updatedInviteList = inviteList.filter(item => item !== inviteUnit);
    setInviteList(updatedInviteList);
  }

  const SearchMember = async() => {
    try {
      const res = await axios.get(`${BASE_URL}/api/user/find_user/${searchInput}`, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      console.log(res.data.response)
      setSearchResult(res.data.response)
    }catch(err) {
      console.log(err)
    }
  }

  const WatchSome = () => {
    console.log(inviteList)
  }

  useEffect(() => {
    const fetchData = async () => {

      
      try {
        const res = await axios.get(`${BASE_URL}/api/friend/list`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('검색결과:', res.data.response.list)
        setFriendList(res.data.response.list)
      }
      catch(err) {
        console.log(err)
      }
    }

    fetchData();
  }, []);

  const InviteMoimFriend = async() => {
    const data = {
      "moimId" : 1,
      "invite" : inviteList
    }
    try {
      const response = await axios.post(`http://j9d108.p.ssafy.io:9999/api/moim/invite`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      console.log(response.data)
    } catch(error) {
      console.log('error:', error)
    }
  }



  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <div className={styles.requesttext}>
            <p style={{color: '#7677E8', borderBottom: '4px solid #7677E8'}}>초대하기</p>
          </div>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.searchbox}>
            <div className={styles.inputgroup}>
              <div className={styles.inputlabel}>
                <h2>전화번호</h2>
              </div>
              <div className={styles.searchgroup}>
                <div className={styles.inputbox}>
                  <input type="text" onChange={ChangeSearchInput} value={searchInput}/>
                </div>
                <div className={styles.button}>
                  <button onClick={SearchMember}>검색</button>
                </div>
              </div>
            </div>
          </div>
          
          <div className={styles.searchresult} onClick={() => AppendSearchUnit(searchResult)}>
            {searchResult && searchResult.accountNumber ? (
              <div className={styles.searchresultunit}>
                <div className={styles.usercharacter}>
                  <div className={styles.userImg}>
                    <img src="" alt="" />
                  </div>
                </div>
                <div className={styles.useraccount}>
                  <h2 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.nickName}</h2>
                  {searchResult.accountNumber == "대표계좌가 없습니다." ? (
                    <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.phoneNumber}</h3>
                    ):(
                    <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.accountNumber}</h3>
                  )}
                </div>
                <div className={styles.appendbtn}>
                  <button>추가</button>
                </div>
              </div>
            ):(
              <></>
            )}
          </div>

          <div className={styles.friendlist}>
            <div className={styles.friendcontent}>  
              <div className={styles.listlabel}>
                <h2>친구 리스트</h2>
              </div>
              <div className={styles.friendbox}>
                {friendList.length > 0 && friendList.map((friend, index) => (
                  // <div className={styles.friendunit} onClick={() => AppendInviteList(friend)} key={index}>
                    <div className={styles.searchresultunit} onClick={() => AppendInviteList(friend)} key={index}>
                      <div className={styles.usercharacter}>
                        <div className={styles.userImg}>
                          <img src="" alt="" />
                        </div>
                      </div>
                      <div className={styles.useraccount}>
                        <h2 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{friend.friendId}</h2>
                        {searchResult.accountNumber == "대표계좌가 없습니다." ? (
                          <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.phoneNumber}</h3>
                          ):(
                          <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.accountNumber}</h3>
                        )}
                      </div>
                      <div className={styles.appendbtn}>
                        <button>추가</button>
                      </div>
                    </div>
                  // </div>
                ))}
              </div>
            </div>
          </div>

          <div className={styles.invitelist}>
            <div className={styles.invitecontent}>  
              <div className={styles.listlabel}>
                <h2>초대 리스트</h2>
              </div>
              <div className={styles.invitebox}>
                {inviteList.length > 0 && (
                  <div className={styles.inviteunitContainer}>
                    {inviteList.map((inviteUnit, index) => (
                      <div className={styles.inviteunit} key={index} onClick={() => DeleteUnit(inviteUnit)}>
                        <MemberUnit userId={inviteUnit.userId}/>
                      </div>
                    ))}
                  </div>
                )}
              </div>

            </div>
          </div>

        </div>

        <div className={styles.infobtns}>
          <button onClick={WatchSome}>aaa</button>
          <button onClick={ModalClose}>닫기</button>
          <button onClick={InviteMoimFriend}>초대하기</button>
        </div>

      </div>
    </div>
  );
}

export default InviteModal;
