package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AccountMapper {

    @Mapping(source = "type", target = "accountType")
    @Mapping(source = "currency", target = "currencyCode")
    Account map(AccountDto accountDto);

    @Mapping(source = "accountType", target = "type")
    @Mapping(source = "currencyCode", target = "currency")
    @Mapping(source = "openDate", target = "openDate", qualifiedByName = "formatDate")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "formatDate")
    AccountDto mapDto(Account account);

    @Mapping(source = "accountType", target = "type")
    @Mapping(source = "currencyCode", target = "currency")
    @Mapping(source = "openDate", target = "openDate", qualifiedByName = "formatDate")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "formatDate")
    AccountInfoDto mapInfoDto(Account account);

    List<AccountDto> mapDtos(List<Account> accounts);

    @Mapping(source = "type", target = "accountType")
    @Mapping(source = "currency", target = "currencyCode")
    void update(AccountDto accountDto, @MappingTarget Account account);
}
