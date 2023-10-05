import React, { useCallback, useEffect } from "react"
import "./password.css"
import axios from "axios"
import { BASE_URL } from "../../constants"
import { useNavigate } from "react-router-dom"

type Props = {
  setModalOpen(id: boolean) : void
  data: {
    password: string
  }
}



function Password ({setModalOpen, data}: Props) {
  const [nums, setNums] = React.useState<number[]>([])
  const [password, setPassword] = React.useState<string>("")
  const [pwd1, setPwd1] = React.useState<boolean>(false)
  const [pwd2, setPwd2] = React.useState<boolean>(false)
  const [pwd3, setPwd3] = React.useState<boolean>(false)
  const [pwd4, setPwd4] = React.useState<boolean>(false)

  const dataload = () => {
    console.log(data)
  }
  const navigate = useNavigate()
  const PASSWORD_MAX_LENGTH = 4 // 비밀번호 입력길이 제한 설정
  const Close = () => {
    setModalOpen(false)
  }
  useEffect(() => {
    const nums_random = Array.from({ length: 10 }, (v, k) => k) // 이 배열을 변경해 입력문자 변경 가능
    setNums(shuffle(nums_random))
  },[])

  useEffect(() => {
    if (password.length === 0) {
      setPwd1(false)
      setPwd2(false)
      setPwd3(false)
      setPwd4(false)
    }
    else if (password.length === 1) {
      setPwd1(true)
      setPwd2(false)
      setPwd3(false)
      setPwd4(false)
    }
    else if (password.length === 2) {
      setPwd2(true)
      setPwd3(false)
      setPwd4(false)
    }
    else if (password.length === 3) {
      setPwd3(true)
      setPwd4(false)
    }
    else if (password.length === 4) {
      setPwd4(true)
      data.password = password
      axios.post(`${BASE_URL}/bank/account/transfer`, data)
      .then((res) => {
        if (res.data.error) {
          navigate('/transfer-result', {state: {
            message: res.data.error.message
          }})
        }
        else {
          navigate('/transfer-result', {state: {
            message: res.data.response
          }})
        }
        console.log(res)
      })
    }
  },[password])

  const shuffle = (nums: number[]) => {
    // 배열 섞는 함수
    let num_length = nums.length
    while (num_length) {
      const random_index = Math.floor(num_length-- * Math.random())
      const temp = nums[random_index]
      nums[random_index] = nums[num_length]
      nums[num_length] = temp
    }
    return nums
  }

  const handlePasswordChange = useCallback(
    (num: number) => {
      if (password.length === PASSWORD_MAX_LENGTH) {
        return
      }
      setPassword(password + num.toString())
    },
    [password],
  )

  const erasePasswordOne = useCallback(
    () => {
      setPassword(password.slice(0, password.length === 0 ? 0 : password.length - 1))
    },
    [password],
  )

  const erasePasswordAll = useCallback(() => {
    setPassword("")
  }, [])

  const shuffleNums = useCallback(
    (num: number) => () => {
      handlePasswordChange(num)
    },
    [handlePasswordChange],
  )



  return (
    <div className="ModalContainer">
      <div className="PwdContainer">

      <button className="CloseBtn" onClick={Close}>x</button>
      <button onClick={dataload}>확인</button>
    <div className='pwd'>
        <div className="pwdbox">{pwd1 ? '.':null}</div>
        <div className="pwdbox">{pwd2 ? '.':null}</div>
        <div className="pwdbox">{pwd3 ? '.':null}</div>
        <div className="pwdbox">{pwd4 ? '.':null}</div>
    </div>
      <div className='inputter__flex'>
        {nums.map((n, i) => {
          const Basic_button = (
            <button
              className='num-button__flex spread-effect fantasy-font__2_3rem'
              value={n}
              onClick={shuffleNums(n)}
              key={i}
            >
              {n}
            </button>
          )
          return i == nums.length - 1 ? (
            <>
              <button
                className='num-button__flex spread-effect fantasy-font__2_3rem'
                onClick={erasePasswordAll}
              >
                Clear
              </button>
              {Basic_button}
            </>
          ) : (
            Basic_button
          )
        })}
        <button
          className='num-button__flex spread-effect fantasy-font__2_3rem'
          onClick={erasePasswordOne}
        >
          ←
        </button>
      </div>
      <div>
      </div>
      </div>
      <div className="backdrop" onClick={Close}>

      </div>
    </div>
  )
}

export default Password