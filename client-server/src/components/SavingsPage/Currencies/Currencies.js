import 'bootstrap/dist/css/bootstrap.css';
import ButtonAdd from '../ButtonAdd/ButtonAdd';
import Currency from './Currency/Currency';
import axios from 'axios';
import { useState, useEffect } from 'react';
import CurrencyForm from './CurrencyForm/CurrencyForm';
import CurrencyUpdateForm from './CurrencyUpdateForm/CurrencyUpdateForm';

const Currencies = (props) => {

  const jwtToken = props.jwtToken;
  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  };
  const updateProfit = props.updateProfit
  const updateInvested = props.updateInvested
  const [currencies, setCurrencies] = useState([])
  const [availCurrData, setAvailCurrData] = useState([])
  const [showFrom, setShowForm] = useState(false)
  const handleClose = () => setShowForm(false)
  const handleShow = () => setShowForm(true)
  
  const [showUpdateForm, setShowUpdateForm] = useState(false)
  const handleUpdateClose = () => setShowUpdateForm(false)
  const handleUpdateShow = () => setShowUpdateForm(true)
  const [updateId, setUpdateId] = useState(1)
  const [updateQuantity, setUpdateQuantity] = useState(0.01)
  const [updateAvgBought, setUpdateAvgBought] = useState(1)
  const [updateCurrencyName, setUpdateCurrencyName] = useState("")


  useEffect(() => {
    const getCurrencies = async () => {
      const currenciesFromServer = await fetchCurrencies()
      setCurrencies(currenciesFromServer.data)
    }
    getCurrencies()
    getCurrenciesData()
  }, [])

  const removeCurrData = (data) => {
    const filteredArray = availCurrData.filter((item) => item.id !== data.id)
    setAvailCurrData(filteredArray)
  }

  const getCurrenciesData = async () => {
    const currDataFroServer = await fetchCurrenciesData()
    setAvailCurrData(currDataFroServer.data)
  }

  const onAdd = async (currency) => {
    const newCurrency = await addCurrency(currency)
    const newCurrencyData = newCurrency.data
    removeCurrData(newCurrencyData.currencyData)
    handleClose()
    setCurrencies([...currencies, newCurrencyData])
  }

  const onDelete = async (id) => {
    const deleteResponse = await deleteCurrency(id)
    if(deleteResponse.data == true) {
      setCurrencies(currencies.filter((c) => c.id !== id))
      getCurrenciesData()
    }
    else {
      alert('Error while deleting currency')
    }   
  }

  const onUpdate = async (id, currency) => {
    const updateResponse = await updateCurrency(id, currency)
    if(updateResponse.status == 200) {
      const updCurrency = updateResponse.data
      setCurrencies(
        currencies.map((c) =>
          c.id === id ? { ...c, quantity: updCurrency.quantity, avgBought: updCurrency.avgBought } : c
        ))
    }
    else {
      alert('Error while deleting currency')
    }
    setShowUpdateForm(false)
  }

  const onUpdateForm = (id, currency) => {
    setUpdateQuantity(currency.quantity)
    setUpdateAvgBought(currency.avgBought)
    setUpdateCurrencyName(currency.currencyName)
    setUpdateId(id)
    setShowUpdateForm(true)
  }

  const calculateProfit = () => {
    let sum = 0
    for (const el of currencies) {
      sum += Math.round((el.currencyData.currentPrice-el.avgBought) * el.quantity * 100) / 100;
    }
    return sum
  }

  const calculateInvested = () => {
    let sum = 0
    for (const el of currencies) {
      sum += Math.round(el.avgBought * el.quantity * 100) / 100;
    }
    return sum
  }

  //fetch currencies
  const fetchCurrencies = () => {
    return axios.get("/savings/currencies",authHeader)
  }

  //fetch available currencies data
  const fetchCurrenciesData = () => {
    return axios.get("/savings/available-currency-data",authHeader)
  }

  //add currency
  const addCurrency = async(currency) => {
    return axios.post("/savings/currencies", currency, authHeader)
  }

  //delete currency
  const deleteCurrency = async(id) => {
    return axios.delete(`/savings/currencies/${id}`, authHeader)
  }
  //update currency
  const updateCurrency = async(id, currency) => {
    return axios.put(`/savings/currencies/${id}`, currency, authHeader)
  }

  return (
    <div className="row pb-3 pt-3">
      {updateProfit(calculateProfit()) /* set currency profit after render */}
      {updateInvested(calculateInvested()) /* set currency invested after render */}
      { availCurrData.length > 0 && //show button if data can be added
        <div className="col-sm-3">
          <ButtonAdd description={"Add currency"} handleShow={handleShow} />
        </div>
      }
      {currencies.map(c => <Currency curr={c} key={c.id} onDelete={onDelete} onUpdateForm={onUpdateForm} />
      )}
      {showFrom && <CurrencyForm 
                      availCurrData={availCurrData} 
                      showFrom={handleShow}
                      handleClose={handleClose}
                      onAdd={onAdd}
                  />}
      {showUpdateForm && <CurrencyUpdateForm
                            quantity={updateQuantity}
                            avgBought={updateAvgBought}
                            currencyName={updateCurrencyName}
                            id={updateId}
                            onUpdate={onUpdate}
                            showFrom={handleUpdateShow}
                            handleClose={handleUpdateClose}
                        />}                                     
    </div>
  )
}

export default Currencies